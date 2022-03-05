package com.dmj.sqldsl.service;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.executor.transaction.Propagation;
import com.dmj.sqldsl.executor.transaction.TransactionDefinition;
import com.dmj.sqldsl.executor.transaction.TransactionException;
import com.dmj.sqldsl.executor.transaction.TransactionStatus;
import com.dmj.sqldsl.service.support.ServiceTransactionTest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TransactionTest extends ServiceTransactionTest {

  @Test
  public void should_commit_all_when_do_transaction_given_no_exception_throw() {
    TransactionStatus status = transactionManager.getTransaction(definition);

    service.save(new TypeUser("tom", 18, 2));
    TypeUser user = new TypeUser();
    user.setId(1);
    user.setName("alice");
    service.save(user);
    transactionManager.commit(status);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "alice", 17, 1),
        new TypeUser(2, "tom", 18, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_rollback_all_when_do_transaction_given_real_rollback() {
    TransactionStatus status = transactionManager.getTransaction(definition);

    service.save(new TypeUser("tom", 18, 2));
    TypeUser user = new TypeUser();
    user.setId(1);
    user.setName("alice");
    service.save(user);
    transactionManager.rollback(status);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Collections.singletonList(
        new TypeUser(1, "bob", 17, 1)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_read_nothing_when_do_transaction_given_read_committed_with_no_commit() {
    TransactionStatus status = transactionManager.getTransaction(definition);
    service.save(new TypeUser("tom", 18, 2));
    TypeUser user = new TypeUser();
    user.setId(1);
    user.setName("alice");
    service.save(user);
    List<TypeUser> actual = supplyAsync(() -> service.select(TypeUser.class)).join();
    transactionManager.commit(status);

    List<TypeUser> expected = Collections.singletonList(
        new TypeUser(1, "bob", 17, 1)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_throw_exception_when_do_transaction_given_rollback_finished_transaction() {
    TransactionStatus status = transactionManager.getTransaction(definition);

    service.save(new TypeUser("tom", 18, 2));
    transactionManager.commit(status);

    assertThrows(TransactionException.class, () -> transactionManager.rollback(status));
  }

  @Test
  public void should_throw_exception_when_do_transaction_given_commit_finished_transaction() {
    TransactionStatus status = transactionManager.getTransaction(definition);

    service.save(new TypeUser("tom", 18, 2));
    transactionManager.commit(status);

    assertThrows(TransactionException.class, () -> transactionManager.commit(status));
  }

  @Test
  public void should_commit_inside_tx_when_do_transaction_given_isolated_propagation() {
    TransactionStatus outsideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("tom", 18, 2));
    TransactionStatus insideStatus = transactionManager.getTransaction(
        new TransactionDefinition(Propagation.ISOLATE));
    service.save(new TypeUser("alice", 18, 2));
    transactionManager.commit(insideStatus);
    transactionManager.rollback(outsideStatus);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "bob", 17, 1),
        new TypeUser(2, "alice", 18, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_commit_inside_tx_when_do_transaction_given_isolated_propagation_and_mix_opr() {
    TransactionStatus outsideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("tom", 18, 2));
    TransactionStatus insideStatus = transactionManager.getTransaction(
        new TransactionDefinition(Propagation.ISOLATE));
    service.save(new TypeUser("alice", 18, 2));
    transactionManager.rollback(outsideStatus);
    transactionManager.commit(insideStatus);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "bob", 17, 1),
        new TypeUser(2, "alice", 18, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_commit_all_when_do_transaction_given_into_propagation() {
    TransactionStatus outsideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("tom", 18, 2));
    TransactionStatus insideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("alice", 18, 2));
    transactionManager.commit(insideStatus);
    transactionManager.commit(outsideStatus);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "bob", 17, 1),
        new TypeUser(2, "tom", 18, 2),
        new TypeUser(2, "alice", 18, 2)
    );
    assertEquals(expected, actual);
  }

  @Test
  public void should_throw_exception_when_do_transaction_given_into_propagation_mix_opr() {
    TransactionStatus outsideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("tom", 18, 2));
    TransactionStatus insideStatus = transactionManager.getTransaction(definition);
    service.save(new TypeUser("alice", 18, 2));
    transactionManager.commit(outsideStatus);
    List<TypeUser> actual = service.select(TypeUser.class);

    List<TypeUser> expected = Arrays.asList(
        new TypeUser(1, "bob", 17, 1),
        new TypeUser(2, "tom", 18, 2),
        new TypeUser(2, "alice", 18, 2)
    );
    assertEquals(expected, actual);
    assertThrows(TransactionException.class, () -> transactionManager.commit(insideStatus));
  }
}
