package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.driver.SqlDialect;
import com.dmj.sqldsl.driver.SqlDslExecutor;
import com.dmj.sqldsl.dto.AliasUser;
import com.dmj.sqldsl.dto.NameUser;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import com.dmj.sqldsl.platform.DatabaseManager;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;

@Database
public class SingleTableTest {

  private SqlDslExecutor executor;

  @BeforeAll
  static void beforeAll() {
    DatabaseManager.executeSqlFile(SingleTableTest.class.getSimpleName());
  }

  @AfterAll
  static void afterAll() {
    DatabaseManager.cleanDatabase();
  }

  @BeforeEach
  void beforeEach(SqlDialect dialect) {
    executor = new SqlDslExecutor(dialect, new DatabaseManager());
  }

  @TestTemplate
  public void should_return_all_user_when_select_all_given_no_condition() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(1, "alice", 16),
        new User(2, "bob", 17),
        new User(3, "tom", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_alice_user_when_select_by_id_given_id_is_1() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(new User(1, "alice", 16));
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_bob_user_when_select_by_name_given_name_is_bob() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(eq(User::getName, "bob"))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(new User(2, "bob", 17));
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_two_user_when_select_by_or_condition_given_id_is_1_or_2() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(eq(User::getId, 1).or().eq(User::getId, 2))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(1, "alice", 16),
        new User(2, "bob", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_two_user_when_select_by_nested_or_condition_given_nested_condition() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(eq(User::getId, 1)
            .or(x -> x.eq(User::getAge, 17).eq(User::getName, "tom")))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(1, "alice", 16),
        new User(3, "tom", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_one_user_with_select_fields_when_select_fields() {
    DslQuery query = DslQueryBuilder
        .select(User::getId, User::getName)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(
        new User(1, "alice", null)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_one_name_user_when_select_with_dto_given_result_is_dto() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<NameUser> result = executor.execute(query, NameUser.class);

    List<NameUser> expected = singletonList(
        new NameUser("alice")
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_one_user_when_select_given_multiple_select_function() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .select(User::getId, User::getName)
        .selectAll(User.class)
        .select(User::getAge)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(
        new User(1, "alice", 16)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_with_alias_when_select_given_column_alias() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .selectAs(User::getName, AliasUser::getUserName)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<AliasUser> result = executor.execute(query, AliasUser.class);

    List<AliasUser> expected = singletonList(new AliasUser(1, "alice", 16));
    assertEquals(expected, result);
  }
}
