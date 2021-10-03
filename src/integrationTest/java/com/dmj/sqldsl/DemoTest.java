package com.dmj.sqldsl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class DemoTest {

  @Container
  private static final MySQLContainer<?> MY_SQL_CONTAINER =
      new MySQLContainer<>("mysql:5.7.34");

  @Container
  private final PostgreSQLContainer<?> postgresqlContainer =
      new PostgreSQLContainer<>("postgres:9.6.12")
          .withDatabaseName("foo")
          .withUsername("foo")
          .withPassword("secret");

  @Test
  void test() {
    assertTrue(MY_SQL_CONTAINER.isRunning());
    assertTrue(postgresqlContainer.isRunning());
  }

}
