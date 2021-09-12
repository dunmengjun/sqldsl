package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.dto.UserComment;
import com.dmj.sqldsl.entity.Comment;
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

  @Test
  public void should_return_page_user_when_select_all_given_limit() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class)
        .from(User.class)
        .limit(1)
        .toQuery();

    List<User> result = driver.execute(query, User.class);

    List<User> expected = singletonList(
        new User(1, "alice", 16)
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_page_user_when_select_all_given_joined_and_paged() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .limit(0, 1)
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = singletonList(
        new UserComment(1, "alice", "test massage1")
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_page_user_when_select_all_given_joined_and_has_conditions_and_paged() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getId, 1))
        .limit(0, 1)
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = singletonList(
        new UserComment(1, "alice", "test massage1")
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_page_user_when_select_all_given_joined_and_has_conditions_and_limit() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getId, 1))
        .limit(1)
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = singletonList(
        new UserComment(1, "alice", "test massage1")
    );
    assertEquals(expected, result);
  }
}
