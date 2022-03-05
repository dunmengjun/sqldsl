package com.dmj.sqldsl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.service.support.ServiceTransactionTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TransactionTest extends ServiceTransactionTest {

  @Test
  public void should_commit_all_when_do_transaction_given_no_exception_throw() {
    transactionManager.doTransaction(transactionStatus -> {
      service.save(new TypeUser("tom", 18, 2));
      TypeUser user = new TypeUser();
      user.setId(1);
      user.setName("alice");
      service.save(user);
      transactionStatus.commit();
    });
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "alice", 17, 1),
        new TypeUser(2, "tom", 18, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_rollback_all_when_do_transaction_given_real_rollback() {
    transactionManager.doTransaction(transactionStatus -> {
      service.save(new TypeUser("tom", 18, 2));
      TypeUser user = new TypeUser();
      user.setId(1);
      user.setName("alice");
      service.save(user);
      transactionStatus.rollback();
    });
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Collections.singletonList(
        new TypeUser(1, "bob", 17, 1)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_rollback_update_when_do_transaction_given_only_rollback_update() {
    transactionManager.doTransaction(transactionStatus -> {
      service.save(new TypeUser("tom", 18, 2));
      transactionStatus.commit();
      TypeUser user = new TypeUser();
      user.setId(1);
      user.setName("alice");
      service.save(user);
      transactionStatus.rollback();
    });
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "bob", 17, 1),
        new TypeUser(2, "tom", 18, 2)
    );
    assertEquals(expected, actual);
  }
}
