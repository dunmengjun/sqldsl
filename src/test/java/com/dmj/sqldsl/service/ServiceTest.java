package com.dmj.sqldsl.service;

import com.dmj.sqldsl.executor.SqlDialect;
import com.dmj.sqldsl.executor.SqlDslExecutor;
import com.dmj.sqldsl.platform.DatabaseManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceTest {

  protected SqlDslService service = new SqlDslService(
      new SqlDslExecutor(SqlDialect.h2, new DatabaseManager()));

  @BeforeAll
  void beforeAll() {
    DatabaseManager.executeSqlFile(this.getClass().getSimpleName());
  }

  @AfterAll
  void afterAll() {
    DatabaseManager.cleanDatabase();
  }
}
