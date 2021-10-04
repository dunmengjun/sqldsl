package com.dmj.sqldsl;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
class DemoTest {

  private static final List<JdbcDatabaseContainer<?>> containers = Arrays.asList(
      new MySQLContainer<>("mysql:5.7.34"),
      new PostgreSQLContainer<>("postgres:9.6.12"),
      new OracleContainer("gvenzl/oracle-xe")
          .withPassword("foobar")
          .withEnv("ORACLE_PASSWORD", "foobar"),
      new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2019-latest")
          .acceptLicense()
  );

  @Test
  void test() {
    for (JdbcDatabaseContainer<?> container : containers) {
      container.start();
      log.info("==========================");
      log.info("Driver Class: {}", container.getDriverClassName());
      log.info("Username: {}", container.getUsername());
      log.info("Password: {}", container.getPassword());
      log.info("Url: {}", container.getJdbcUrl());
      container.stop();
      container.close();
    }
  }
}
