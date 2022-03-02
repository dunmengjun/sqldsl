package com.dmj.sqldsl.executor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class BatchResultTest {

  @Test
  void should_return_correct_batch_result_when_create_given_correct_param() {
    BatchResult actual = BatchResult.create(
        Arrays.asList(0, 1, 2),
        new int[]{1, 1, 1}
    );

    assertEquals(3, actual.getResultMap().size());
    assertEquals(1, actual.getResultMap().get(0));
    assertEquals(1, actual.getResultMap().get(1));
    assertEquals(1, actual.getResultMap().get(2));
    assertEquals(3, actual.getSuccessful());
    assertEquals(0, actual.getFailed());
    assertEquals(3, actual.getAffectedRows());
  }
}