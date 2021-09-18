# sqldsl

基于jdbc实现的sql dsl，类似Querydsl, 但不需要插件就可提供类型安全的查询(基于SerializedLambda)。可以很方便的和各种类型的库(比如mybatis,
hibernate等)集成。
(项目还处于早期阶段，目前还没有release, 目前测试可以，请不要用于生产环境)

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