package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.ge;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.in;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.lt;
import static com.dmj.sqldsl.platform.H2Mode.h2;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.table.SubQueryBuilder;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.TestTemplate;

@Database({h2})
public class SubQueryTest extends DatabaseTest {

  @TestTemplate
  public void should_return_user_in_sub_query_when_select_given_the_sub_query() {
    DslQueryBuilder subQuery = DslQueryBuilder
        .select(User::getName)
        .from(User.class)
        .where(ge(User::getAge, 17));
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(in(User::getName, subQuery))
        .toQuery();

    List<User> actual = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(2, "bob", 17),
        new User(3, "tom", 17)
    );
    assertEquals(expected, actual);
  }

  @TestTemplate
  public void should_return_user_eq_16_when_select_given_the_sub_query() {
    DslQueryBuilder queryBuilder = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(lt(User::getAge, 17));
    SubQueryBuilder subQuery = SubQueryBuilder.alias(queryBuilder);
    DslQuery query = DslQueryBuilder
        .selectAll(subQuery)
        .from(subQuery)
        .where(eq(subQuery.col(User::getAge), 16))
        .toQuery();

    List<User> actual = executor.execute(query, User.class);

    List<User> expected = singletonList(
        new User(1, "alice", 16)
    );
    assertEquals(expected, actual);
  }

  @TestTemplate
  public void should_return_user_eq_16_when_select_given_the_left_join_sub_query() {
    DslQueryBuilder queryBuilder = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(lt(User::getAge, 17));
    SubQueryBuilder subQuery = SubQueryBuilder.alias(queryBuilder);
    DslQuery query = DslQueryBuilder
        .selectAll(subQuery)
        .from(User.class)
        .leftJoin(subQuery, eq(subQuery.col(User::getId), User::getId))
        .where(eq(subQuery.col(User::getAge), 16))
        .toQuery();

    List<User> actual = executor.execute(query, User.class);

    List<User> expected = singletonList(
        new User(1, "alice", 16)
    );
    assertEquals(expected, actual);
  }
}
