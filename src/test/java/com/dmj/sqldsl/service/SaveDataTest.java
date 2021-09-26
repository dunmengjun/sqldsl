package com.dmj.sqldsl.service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.entity.TypeUser;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SaveDataTest extends ServiceTest {

  @Test
  public void should_insert_into_database_correctly_when_save_entity_given_no_id() {
    TypeUser entity = new TypeUser("alice", 16, 1);

    service.save(entity);
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .eq(TypeUser::getId, 2)
    );

    List<TypeUser> expected = singletonList(new TypeUser(2, "alice", 16, 1));
    assertEquals(expected, actual);
  }

  @Test
  public void should_update_correctly_when_save_entity_given_with_id() {
    TypeUser entity = new TypeUser(1, "b bb", 16, 1);

    service.save(entity);
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .eq(TypeUser::getId, 1)
    );

    List<TypeUser> expected = singletonList(new TypeUser(1, "b bb", 16, 1));
    assertEquals(expected, actual);
  }

  @Test
  public void should_update_correctly_when_save_entity_given_with_id_with_null_field() {
    TypeUser entity = new TypeUser(1, null, 16, 1);

    service.save(entity);
    List<TypeUser> actual = service.select(
        new Wrapper<>(TypeUser.class)
            .eq(TypeUser::getId, 1)
    );

    List<TypeUser> expected = singletonList(new TypeUser(1, null, 16, 1));
    assertEquals(expected, actual);
  }
}
