package com.dmj.sqldsl.service.support;

import com.dmj.sqldsl.executor.SqlDialect;
import com.dmj.sqldsl.executor.SqlDslExecutor;
import com.dmj.sqldsl.platform.DatabaseManager;
import com.dmj.sqldsl.service.SqlDslService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceSelectTest {

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
