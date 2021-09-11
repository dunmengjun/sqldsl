package com.dmj.sqldsl;

import static com.dmj.sqldsl.util.FileUtils.getUri;

import com.dmj.sqldsl.driver.ConnectionManager;
import com.dmj.sqldsl.util.FileUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class DatabaseManager implements ConnectionManager {

  static String url = "jdbc:h2:~/test;MODE=MYSQL";
  static String user = "sa";
  static String password = "";

  static {
    try {
      Class.forName("org.h2.Driver");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void execStmt(Consumer<Statement> consumer) {
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      try (Statement stmt = connection.createStatement()) {
        consumer.accept(stmt);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void executeSqlFile(String fileName) {
    execStmt(statement -> {
      StringBuilder builder = new StringBuilder();
      FileUtils.readLines(getUri(fileName + ".sql"), line -> {
        builder.append(line);
        if (line.trim().isEmpty()) {
          execute(statement, builder.toString());
          builder.delete(0, builder.length());
        }
      });
      execute(statement, builder.toString());
    });
  }

  private static void execute(Statement statement, String sql) {
    try {
      statement.execute(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void cleanDatabase() {
    try (Connection connection = DriverManager.getConnection(url, user, password)) {
      try (ResultSet tables = connection.getMetaData()
          .getTables(null, null, null, new String[]{"TABLES"})) {
        try (Statement statement = connection.createStatement()) {
          while (tables.next()) {
            statement.execute("DELETE FROM " + tables.getObject("TABLE_NAME"));
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }
}
