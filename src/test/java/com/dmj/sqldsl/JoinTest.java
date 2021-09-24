package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static com.dmj.sqldsl.platform.H2Mode.h2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.SelectBuilder;
import com.dmj.sqldsl.builder.exception.JoinTableRepeatedlyException;
import com.dmj.sqldsl.dto.CommentRating;
import com.dmj.sqldsl.dto.UserComment;
import com.dmj.sqldsl.entity.Comment;
import com.dmj.sqldsl.entity.Satisfaction;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestTemplate;

@Database({h2})
public class JoinTest extends DatabaseTest {

  @TestTemplate
  public void should_return_user_alice_with_comment_when_select_all_given_left_join() {
    DslQuery query = new SelectBuilder()
        .selectAll(User.class, User::getId, User::getAge)
        .selectAll(Comment.class, Comment::getUserId)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getAge, 17))
        .toQuery();

    List<UserComment> result = executor.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(3, "bob", "hello world"),
        new UserComment(4, "tom", "hi"),
        new UserComment(null, "weak", null)
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_comment_when_select_given_left_join_and_special_column() {
    DslQuery query = new SelectBuilder()
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(User::getId, 1))
        .toQuery();

    List<UserComment> result = executor.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2")
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_comment_when_select_given_right_join_and_special_column() {
    DslQuery query = new SelectBuilder()
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .rightJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1))
        .toQuery();

    List<UserComment> result = executor.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2"),
        new UserComment(5, null, "hello")
    );
    assertEquals(expected, result);
  }

  @TestTemplate
  public void should_return_user_comment_when_select_given_inner_join_and_special_column() {
    DslQuery query = new SelectBuilder()
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .innerJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1))
        .toQuery();

    List<UserComment> result = executor.execute(query, UserComment.class);

    List<UserComment> expected = Arrays.asList(
        new UserComment(1, "alice", "test massage1"),
        new UserComment(2, "alice", "test massage2")
    );
    assertEquals(expected, result);
  }


  @TestTemplate
  public void should_throw_exception_when_select_given_join_the_same_table_twice() {
    DslQueryBuilder where = new SelectBuilder()
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .innerJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .where(eq(Comment::getStatus, 1));

    Assertions.assertThrows(JoinTableRepeatedlyException.class, where::toQuery);
  }

  @TestTemplate
  public void should_return_comment_rating_when_select_given_two_left_join() {
    DslQuery query = new SelectBuilder()
        .select(User::getName)
        .select(Comment::getId, Comment::getMessage)
        .select(Satisfaction::getRating)
        .from(User.class)
        .leftJoin(Comment.class, eq(User::getId, Comment::getUserId))
        .leftJoin(Satisfaction.class, eq(Comment::getId, Satisfaction::getCommentId))
        .where(eq(Comment::getStatus, 1))
        .toQuery();

    List<CommentRating> result = executor.execute(query, CommentRating.class);

    List<CommentRating> expected = Arrays.asList(
        new CommentRating(1, "alice", "test massage1", 1),
        new CommentRating(1, "alice", "test massage1", 3),
        new CommentRating(2, "alice", "test massage2", 4)
    );
    assertEquals(expected, result);
  }
}
