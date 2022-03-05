# Sqldsl
[![Sonarqube-check](https://github.com/dunmengjun/sqldsl/actions/workflows/sonarqube-check.yml/badge.svg)](https://github.com/dunmengjun/sqldsl/actions/workflows/sonarqube-check.yml)&nbsp;&nbsp;&nbsp;[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=dunmengjun_sqldsl&metric=alert_status)](https://sonarcloud.io/dashboard?id=dunmengjun_sqldsl)&nbsp;&nbsp;&nbsp;[![Publish Package](https://github.com/dunmengjun/sqldsl/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/dunmengjun/sqldsl/actions/workflows/gradle-publish.yml)

基于jdbc实现的sql dsl，类似Querydsl, 但不需要插件就可提供类型安全的查询(基于SerializedLambda)。可以很方便的和各种类型的库(比如mybatis,
hibernate等)集成。(项目还处于早期阶段，测试可以，请不要用于生产环境)

### 特点

1. 用java代码写sql语句，不管是简单还是复杂的sql

> 类似querydsl, 和它一样简单明了，但不用生成代码

2. 无代码生成的类型安全保证, 保姆级的sql规则校验

> 不会有processor配置,只会有简单的一个jar包, 类型安全的原理是充分的利用SerializedLambda和java类型机制
>
> 为什么需要类型安全? 因为非类型安全写sql很容易出错(老手也一样), 一旦出错导致的bug要花巨大的代价去修复
>
> 大部分的类型安全会在编译时保证，少部分在运行时保证。什么？你问类型安全为什么不都在编译时保证？
> 原因是一是sql很复杂，并且还要支持子查询，不可能都在编译时保证类型安全，二是,java的泛型，你懂的。

3. 灵活充分的元数据配置

> 比如可以配置基于哪些注解完成表名和列名的扫描，默认是基于javax.persistence, 不过可配置
>
> 能很容易的集成到spring环境，也可以很容易的在非spring去用(项目就是在非spring环境写的)
>
> 由于注解可配置，所以也很容易和其他框架结合，比如mybatis, hibernate等, 集成可用于提供类型安全且灵活的查询
> 而不用去手写sql语句，即容易错误，也不好分析

4. 不提供复杂的包装，只用java就可以实现很复杂的sql

> 比如hibernate的session管理，hql，又比如mybatis的mapper和xml的映射，在作者看来很是眼花缭乱，不知道该用啥写啥
>
> 本项目保证全程用java代码就可以实现很复杂的sql。

5. 翻译到h2,mysql,postgres,oracle,sqlserver2012以上

> 保证一旦你写完一个查询, 能符合常用规则和预期并且正确的翻译到主流数据库

6. 最少的依赖

> 本项目基于jdk8编写只依赖于java标准库,javax.persistence, java.sql和一个日志门面slf4j-api, 再就没有任何依赖了

### 开始

#### Maven

```xml

<dependency>
  <groupId>io.github.dunmengjun</groupId>
  <artifactId>sqldsl</artifactId>
  <version>{latest version}</version>
</dependency>
```

#### Gradle

```groovy
implementation 'io.github.dunmengjun:sqldsl:{latest version}'
```

#### Spring环境下bean的配置(默认需要依赖javax.persistence,java.sql)

```java

@Configuration
public class SqlDslConfiguration {

  @Bean
  public SqlDslService sqlDslService(DataSource dataSource) {
    GlobalConfig.setGlobalColumnNameTranslator(new CamelToUnderlineTranslator());
    GlobalConfig.setGlobalTableNameTranslator(new FirstCharToLowerTranslator());
    return new SqlDslService(new SqlDslExecutor(SqlDialect.mysql, dataSource::getConnection));
  }
}
```

Spring环境和非Spring环境都是用的同一种api，区别只是在spring环境下，sqldslservice被spring所管理，而非spring环境需要手动new出来。

#### 使用案例

#### 1. 简单单表查询

实体类

```java

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Id
  private Integer id;

  @Column
  private String name;

  @Column
  private Integer age;

  @Column
  private Integer type;
}
```

如果项目没有依赖javax.persistence, 而是依赖的orm框架自己的注解，也是可以配置的，sqldsl提供了一个全局配置去替换注解扫描

```java
public class GlobalConfig {

  private static EntityConfig entityConfig = EntityConfig.builder()
      .tableConfig(TableConfig.builder()
          .annotationClass(Table.class)
          .tableNameAttribute("name")
          .build())
      .columnConfig(ColumnConfig.builder()
          .idAnnotationClass(Id.class)
          .columnAnnotationClass(Column.class)
          .columnNameAttribute("name")
          .build())
      .lambdaMethodTranslator(new PojoLikedStyleTranslator())
      .build();
  
  ...

  public static void setEntityConfig(EntityConfig entityConfig) {
    GlobalConfig.entityConfig = entityConfig;
  }
  ...
}
```

这是全局配置的源码，在工程中可以直接调用GlobalConfig.setEntityConfig方法去设置自己项目依赖的注解信息， sqldsl所有关于注解的处理都是在EntityConfig中进行。

普通查询

```java
List<User> actual=sqlDslService.select(User.class);
```

分页查询

```java    
PageRequest request = PageRequest.of(1, 1);
Page<User> actual = sqlDslService.select(request, User.class);
```

条件查询

```java
List<User> actual=sqlDslService.select(
    new Wrapper<>(User.class)
    .eq(User::getType,1)
);
```

limit查询

```java
List<User> actual=sqlDslService.select(
    new Wrapper<>(User.class)
    .eq(User::getType,1)
    .limit(1)
);
```

分组查询

```java
List<User> actual=sqlDslService.select(
    new Wrapper<>(User.class)
    .selectAs(max(User::getAge),User::getAge)
    .groupBy(User::getType)
    .having(w->w.gt(max(User::getAge),17))
);
```

排序查询

```java
List<User> actual=sqlDslService.select(
    new Wrapper<>(User.class)
    .selectAs(max(User::getAge),User::getAge)
    .groupBy(User::getType)
    .orderBy(User::getType,false)
    .limit(2)
);
```

#### 2. 多表复杂查询

实体类就不列出来了

普通join查询

```java
DslQueryBuilder queryBuilder=new SelectBuilder()
    .selectAll(User.class,User::getId,User::getAge)
    .selectAll(Comment.class,Comment::getUserId)
    .from(User.class)
    .leftJoin(Comment.class,eq(User::getId,Comment::getUserId))
    .where(eq(User::getAge,17));

List<UserComment> actual=sqlDslService.select(queryBuilder,UserComment.class);
```

多join查询

```java
DslQueryBuilder queryBuilder=new SelectBuilder()
    .select(User::getName)
    .select(Comment::getId,Comment::getMessage)
    .select(Satisfaction::getRating)
    .from(User.class)
    .leftJoin(Comment.class,eq(User::getId,Comment::getUserId))
    .leftJoin(Satisfaction.class,eq(Comment::getId,Satisfaction::getCommentId))
    .where(eq(Comment::getStatus,1));

List<CommentRating> actual=sqlDslService.select(queryBuilder,CommentRating.class);
```

自连接查询

```java
EntityBuilder selfDept=EntityBuilder.alias(Dept.class);
DslQueryBuilder queryBuilder=new SelectBuilder()
    .selectAll(selfDept)
    .from(Dept.class)
    .leftJoin(selfDept,eq(Dept::getParent,selfDept.col(Dept::getId)))
    .where(eq(Dept::getName,"Development Department"));

List<Dept> actual=sqlDslService.select(queryBuilder,Dept.class);
```

form子查询

```java
DslQueryBuilder queryBuilder=new SelectBuilder()
    .selectAll(User.class)
    .from(User.class)
    .where(lt(User::getAge,17));

SubQueryBuilder subQuery=SubQueryBuilder.alias(queryBuilder);
DslQueryBuilder query=new SelectBuilder()
    .selectAll(subQuery)
    .from(subQuery)
    .where(eq(subQuery.col(User::getAge),16));

List<User> actual=sqlDslService.select(query,User.class);
```

join子查询

```java
DslQueryBuilder queryBuilder=new SelectBuilder()
    .selectAll(User.class)
    .from(User.class)
    .where(lt(User::getAge,17));

SubQueryBuilder subQuery=SubQueryBuilder.alias(queryBuilder);
DslQueryBuilder query=new SelectBuilder()
    .selectAll(subQuery)
    .from(User.class)
    .leftJoin(subQuery,eq(subQuery.col(User::getId),User::getId))
    .where(eq(subQuery.col(User::getAge),16));

List<User> actual=sqlDslService.select(query,User.class);
```

#### 插入和更新

默认: null字段不更新和插入,有ID是更新,无ID是插入。
可以定制化对每一个对象设置是更新还是插入, 还有强制更新插入的字段
详情请看 单元测试SaveDataTest

插入

```java
TypeUser entity=new TypeUser("alice",16,1);

sqlDslService.save(entity);
```

更新

```java
TypeUser entity=new TypeUser(1,"b bb",16,1);

sqlDslService.save(entity);
```

插入和更新的区别只在于有没有ID

批量更新或者插入

```java
List<TypeUser> typeUsers=Arrays.asList(
    new TypeUser(1,null,16,1),
    new TypeUser("bob",16,1),
    new TypeUser("tom",18,2)
);

sqlDslService.save(typeUsers);
```

批量操作会自动判断是更新还是插入(底层会把它们分开并生成更新或者插入的jdbc批量操作)

新增 事务管理 详情请看 单元测试TransactionTest

还有很多的案例就不写在这儿了，需要详细的使用介绍，可以直接浏览单元测试，里面分得很细也很全，代码也很好阅读。
