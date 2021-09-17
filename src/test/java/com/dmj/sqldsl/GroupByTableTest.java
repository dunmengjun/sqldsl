package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.gt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.TestTemplate;

@Database
public class GroupByTableTest extends DatabaseTest {

  @TestTemplate
  public void should_return_user_with_alias_when_select_given_column_alias() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .groupBy(User::getAge, User::getId)
        .having(gt(User::getAge, 16.7))
        .toQuery();

    List<User> actual = executor.execute(query, User.class);
    List<User> expected = Arrays.asList(
        new User(2, "bob", 17),
        new User(3, "tom", 17),
        new User(5, "test", 19)
    );
    assertEquals(expected, actual);
  }
}
