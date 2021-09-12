package com.dmj.sqldsl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PageTableTest {

  private final MysqlDriver driver = new MysqlDriver(new DatabaseManager());

  @BeforeAll
  static void beforeAll() {
    DatabaseManager.executeSqlFile(PageTableTest.class.getSimpleName());
  }

  @AfterAll
  static void afterAll() {
    DatabaseManager.cleanDatabase();
  }

  @Test
  public void should_return_page_user_when_select_all_given_paged() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .limit(0, 2)
        .toQuery();

    List<User> result = driver.execute(query, User.class);

    List<User> expected = Arrays.asList(
        new User(1, "alice", 16),
        new User(2, "bob", 17)
    );
    assertEquals(expected, result);
  }
}
