package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.WhereBuilder;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.dto.UserComment;
import com.dmj.sqldsl.entity.Comment;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinTableTest {

  private final MysqlDriver driver = new MysqlDriver(new DatabaseManager());

  @BeforeAll
  static void beforeAll() {
    DatabaseManager.executeSqlFile(JoinTableTest.class.getSimpleName());
  }

  @AfterAll
  static void afterAll() {
    DatabaseManager.cleanDatabase();
  }

  @Test
  public void should_return_user_alice_with_comment_when_select_all_given_left_join() {
    DslQuery query = DslQueryBuilder
        .selectAll(User.class, User::getId, User::getAge)
        .selectAll(Comment.class, Comment::getUserId)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getAge, 17))
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(3, "bob", "hello world"),
        new UserComment(4, "tom", "hi"),
        new UserComment(null, "weak", null)
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_user_comment_when_select_given_left_join_and_special_column() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getId, 1))
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2")
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_user_comment_when_select_given_right_join_and_special_column() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .rightJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1))
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2"),
        new UserComment(5, null, "hello")
    );
    assertEquals(expected, result);
  }

  @Test
  public void should_return_user_comment_when_select_given_inner_join_and_special_column() {
    DslQuery query = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .innerJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1))
        .toQuery();

    List<UserComment> result = driver.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2")
    );
    assertEquals(expected, result);
  }


  @Test
  public void should_throw_exception_when_select_given_join_the_same_table_twice() {
    WhereBuilder where = DslQueryBuilder
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .innerJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1));

    Assertions.assertThrows(JoinTableRepeatedlyException.class, where::toQuery);
  }

  @Test
  public void should_supported_multiple_table_join_rather_than_only_two() {
    // TODO: 2021/9/12 should supported multiple table join rather than only two
  }
}
