package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.query;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.ge;
import static com.dmj.sqldsl.builder.condition.ConditionBuilders.in;
import static com.dmj.sqldsl.platform.H2Mode.h2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
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
    DslQueryBuilder queryBuilder = DslQueryBuilder
        .select(User::getName)
        .from(User.class)
        .where(ge(User::getAge, 17));
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .where(in(User::getName, query(queryBuilder)))
        .toQuery();

    List<User> actual = executor.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(2, "bob", 17),
        new User(3, "tom", 17)
    );
    assertEquals(expected, actual);
  }
}
