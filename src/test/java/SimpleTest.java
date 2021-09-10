import com.dmj.sqldsl.builder.DslBuilder;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.ColumnAnnotation;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.TableAnnotation;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.model.DslQuery;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.*;
import java.util.List;

public class SimpleTest {
    private static final EntityConfig entityConfig = EntityConfig.builder()
            .tableAnnotation(new TableAnnotation(Table.class, "name"))
            .columnAnnotation(new ColumnAnnotation(Column.class, "name"))
            .build();

    @Test
    public void test1() throws SQLException {
        User user = new User();
        DslQuery query = new DslBuilder()
                .select(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(user::getId, 1)
                        .or(x -> x.eq(user::getAge, 23).eq(user::getName, "alice")))
                .toQuery(entityConfig);
        MysqlDriver driver = new MysqlDriver(DatabaseManager.getConnection());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }

    @Test
    public void test2() throws SQLException {
        User user = new User();
        DslQuery query = new DslBuilder()
                .select(User.class)
                .from(User.class)
                .toQuery(entityConfig);
        MysqlDriver driver = new MysqlDriver(DatabaseManager.getConnection());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }
}
