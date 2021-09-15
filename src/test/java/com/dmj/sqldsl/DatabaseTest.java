package com.dmj.sqldsl;

import com.dmj.sqldsl.executor.SqlDialect;
import com.dmj.sqldsl.executor.SqlDslExecutor;
import com.dmj.sqldsl.platform.DatabaseManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class DatabaseTest {

  protected SqlDslExecutor executor;

  @BeforeAll
  void beforeAll() {
    DatabaseManager.executeSqlFile(this.getClass().getSimpleName());
  }

  @AfterAll
  void afterAll() {
    DatabaseManager.cleanDatabase();
  }

  @BeforeEach
  void beforeEach(SqlDialect dialect) {
    executor = new SqlDslExecutor(dialect, new DatabaseManager());
  }
}
