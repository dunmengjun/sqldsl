package com.dmj.sqldsl.service;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.executor.BatchResult;
import com.dmj.sqldsl.model.ModifiedFlag;
import com.dmj.sqldsl.service.support.ServiceSaveTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SaveDataTest extends ServiceSaveTest {

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

    List<TypeUser> expected = singletonList(new TypeUser(1, "bob", 16, 1));
    assertEquals(expected, actual);
  }

  @Test
  public void should_update_insert_when_save_batch_given_list_entities() {
    TypeUser typeUser = new TypeUser(1, null, 16, 1);
    typeUser.setForceUpdatedColumns(TypeUser::getName);
    TypeUser bob = new TypeUser(5, "bob", 16, 1);
    bob.setModifiedFlag(ModifiedFlag.INSERT);
    List<TypeUser> typeUsers = Arrays.asList(
        typeUser,
        bob,
        new TypeUser("tom", 18, 2)
    );

    BatchResult actualResult = service.save(typeUsers);
    List<TypeUser> actual = service.select(TypeUser.class);

    assertEquals(3, actual.size());
    BatchResult expectedResult = BatchResult.create(
        Arrays.asList(0, 1, 2),
        new int[]{1, 1, 1}
    );
    assertEquals(expectedResult, actualResult);
  }
}
