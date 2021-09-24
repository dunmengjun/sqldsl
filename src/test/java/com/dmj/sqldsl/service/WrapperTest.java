package com.dmj.sqldsl.service;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.max;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.service.page.Page;
import com.dmj.sqldsl.service.page.PageRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class WrapperTest extends ServiceTest {

  @Test
  public void should_return_type_user_list_when_select_given_type_user_class() {
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "alice", 16, 1),
        new TypeUser(2, "bob", 17, 1),
        new TypeUser(3, "tom", 17, 2),
        new TypeUser(4, "dev", 16, 1),
        new TypeUser(5, "test", 19, 3),
        new TypeUser(6, "ba", 16, 3)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_page_type_user_when_select_given_type_user_with_page() {
    PageRequest request = PageRequest.of(1, 1);

    Page<TypeUser> actual = service.select(request, TypeUser.class);

    Page<TypeUser> expected = new Page<>(request, 6,
        singletonList(new TypeUser(1, "alice", 16, 1)));
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_type_user_list_when_select_given_type_user_wrapper_without_condition() {
    List<TypeUser> actual = service.select(new Wrapper<>(TypeUser.class));

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "alice", 16, 1),
        new TypeUser(2, "bob", 17, 1),
        new TypeUser(3, "tom", 17, 2),
        new TypeUser(4, "dev", 16, 1),
        new TypeUser(5, "test", 19, 3),
        new TypeUser(6, "ba", 16, 3)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_type_user_list_when_select_given_type_user_wrapper_with_condition() {
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .eq(TypeUser::getType, 1)
    );

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "alice", 16, 1),
        new TypeUser(2, "bob", 17, 1),
        new TypeUser(4, "dev", 16, 1)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_type_user_list_when_select_given_wrapper_with_condition_and_limit() {
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .eq(TypeUser::getType, 1)
            .limit(1)
    );

    List<TypeUser> expected = singletonList(
            new TypeUser(1, "alice", 16, 1)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_type_user_list_when_select_given_group_by_and_get_max_age() {
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .selectAs(max(TypeUser::getAge), TypeUser::getAge)
            .groupBy(TypeUser::getType)
    );

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(null, null, 17, 1),
        new TypeUser(null, null, 17, 2),
        new TypeUser(null, null, 19, 3)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_type_user_list_when_select_given_group_by_and_get_max_age_lt_17() {
    List<TypeUser> actual = service.select(
            new Wrapper<>(TypeUser.class)
                .selectAs(max(TypeUser::getAge), TypeUser::getAge)
                .groupBy(TypeUser::getType)
                .having(w -> w.gt(max(TypeUser::getAge), 17))
    );

    List<TypeUser> expected = singletonList(
        new TypeUser(null, null, 19, 3)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_return_sorted_type_user_list_when_select_given_group_by_and_order_by() {
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .selectAs(max(TypeUser::getAge), TypeUser::getAge)
            .groupBy(TypeUser::getType)
            .orderBy(TypeUser::getType, false)
            .limit(2)
    );

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(null, null, 19, 3),
        new TypeUser(null, null, 17, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_distinct_the_same_order_by_when_select_given_multiple_order_by() {
    // TODO: 2021/9/24 需要转换为语句生成测试
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .selectAs(max(TypeUser::getAge), TypeUser::getAge)
            .groupBy(TypeUser::getType)
            .orderBy(TypeUser::getType, false)
            .orderBy(TypeUser::getType, false)
            .orderBy(TypeUser::getType, false)
            .limit(2)
    );

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(null, null, 19, 3),
        new TypeUser(null, null, 17, 2)
    );
    assertEquals(expected, actual);
  }
}
