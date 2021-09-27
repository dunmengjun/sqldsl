package com.dmj.sqldsl.executor;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.all;
import static com.dmj.sqldsl.builder.column.ColumnBuilders.count;
import static com.dmj.sqldsl.builder.column.ColumnBuilders.distinct;
import static com.dmj.sqldsl.builder.column.LikeValue.contains;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.between;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.in;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.like;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.notIn;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.notLike;
import static com.dmj.sqldsl.platform.H2Mode.h2;
import static com.dmj.sqldsl.util.DateUtils.parseDate;
import static com.dmj.sqldsl.util.DateUtils.parseRange;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmj.sqldsl.builder.SelectBuilder;
import com.dmj.sqldsl.builder.column.DateRange;
import com.dmj.sqldsl.dto.AliasUser;
import com.dmj.sqldsl.dto.NameUser;
import com.dmj.sqldsl.entity.Book;
import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.executor.exception.ExecutionException;
import com.dmj.sqldsl.executor.support.DatabaseTest;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.TestTemplate;


@Database({h2})
public class SingleTableTest extends DatabaseTest {

  @TestTemplate
  public void should_return_all_user_when_select_all_given_no_condition() {
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
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
    DslQuery query = new SelectBuilder()
        .selectAll(User.class)
        .selectAs(User::getName, AliasUser::getUserName)
        .from(User.class)
        .where(eq(User::getId, 1))
        .toQuery();

    List<AliasUser> result = executor.execute(query, AliasUser.class);

    List<AliasUser> expected = singletonList(new AliasUser(1, "alice", 16));
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_name_contains_o_when_select_given_name_like() {
    DslQuery query = new SelectBuilder()
        .selectAll(User.class)
        .from(User.class)
        .where(like(User::getName, contains("o")))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(2, "bob", 17),
        new User(3, "tom", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_name_not_contains_o_when_select_given_name_not_like() {
    DslQuery query = new SelectBuilder()
        .selectAll(User.class)
        .from(User.class)
        .where(notLike(User::getName, contains("o")))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(new User(1, "alice", 16));
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_in_special_ids_when_select_given_id_in() {
    DslQuery query = new SelectBuilder()
        .selectAll(User.class)
        .from(User.class)
        .where(in(User::getId, 1, 2))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(1, "alice", 16),
        new User(2, "bob", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_not_in_special_ids_when_select_given_id_not_in() {
    DslQuery query = new SelectBuilder()
        .selectAll(User.class)
        .from(User.class)
        .where(notIn(User::getId, 1, 2))
        .toQuery();

    List<User> result = executor.execute(query, User.class);

    List<User> expected = singletonList(
        new User(3, "tom", 17)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_between_special_time_range_when_select_given_between_time_range() {
    DateRange dateRange = parseRange("2021-09-29 19:23:11,2021-09-29 20:23:11");
    DslQuery query = new SelectBuilder()
        .selectAll(Book.class)
        .from(Book.class)
        .where(between(Book::getCreateTime, dateRange))
        .toQuery();

    List<Book> actual = executor.execute(query, Book.class);

    List<Book> expected = singletonList(
        new Book(1, "book1", parseDate("2021-09-29 19:23:11")));
    assertEquals(expected, actual);
  }

  @TestTemplate
  public void should_return_the_count_with_distinct_for_age_when_select_given_count_and_distinct() {
    DslQuery query = new SelectBuilder()
        .select(count(distinct(TypeUser::getAge)))
        .from(User.class)
        .toQuery();

    List<Long> actual = executor.execute(query, Long.class);

    assertEquals(singletonList(2L), actual);
  }

  @TestTemplate
  public void should_throw_exception_when_select_given_primitive_result_type() {
    DslQuery query = new SelectBuilder()
        .select(count(distinct(TypeUser::getAge)))
        .from(User.class)
        .toQuery();

    assertThrows(ExecutionException.class, () -> executor.execute(query, long.class));
  }

  @TestTemplate
  public void should_return_the_count_all_when_select_given_count_all() {
    DslQuery query = new SelectBuilder()
        .select(count(all()))
        .from(User.class)
        .toQuery();

    List<Long> actual = executor.execute(query, Long.class);

    assertEquals(singletonList(3L), actual);
  }
}
