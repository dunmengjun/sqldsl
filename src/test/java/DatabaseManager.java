import com.dmj.sqldsl.driver.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager implements ConnectionManager {

    static String url = "jdbc:h2:~/test;MODE=MYSQL";
    static String user = "sa";
    static String password = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            execStmt(stmt -> {
                stmt.execute("DROP TABLE IF EXISTS user");
                stmt.execute("CREATE TABLE user(id int PRIMARY KEY auto_increment, name VARCHAR(100), age int)");
                stmt.executeUpdate("INSERT INTO user(name, age) VALUES('alice', 16)");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FunctionalInterface
    interface StatementConsumer {
        void accept(Statement statement) throws SQLException;
    }

    private static void execStmt(StatementConsumer consumer) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement stmt = connection.createStatement()) {
                consumer.accept(stmt);
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
