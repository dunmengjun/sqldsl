package com.dmj.sqldsl.service.support;

import com.dmj.sqldsl.executor.SqlDialect;
import com.dmj.sqldsl.executor.SqlDslExecutor;
import com.dmj.sqldsl.platform.DatabaseManager;
import com.dmj.sqldsl.service.SqlDslService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceSaveTest {

  protected SqlDslService service = new SqlDslService(
      new SqlDslExecutor(SqlDialect.h2, new DatabaseManager()));

  @BeforeEach
  void beforeEach() {
    DatabaseManager.executeSqlFile(this.getClass().getSimpleName());
  }

  @AfterEach
  void afterEach() {
    DatabaseManager.cleanDatabase();
  }
}
