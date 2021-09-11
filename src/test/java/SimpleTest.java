import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.ColumnAnnotation;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.builder.config.TableAnnotation;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.model.DslQuery;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.SQLException;
import java.util.List;

public class SimpleTest {
    private static final EntityConfig entityConfig = EntityConfig.builder()
            .tableAnnotation(new TableAnnotation(Table.class, "name"))
            .columnAnnotation(new ColumnAnnotation(Column.class, "name"))
            .build();

    @Test
    public void test1() throws SQLException {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getId, 1)
                        .or(x -> x.eq(User::getAge, 23).eq(User::getName, "alice")))
                .toQuery(entityConfig);
        MysqlDriver driver = new MysqlDriver(DatabaseManager.getConnection());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }

    @Test
    public void test2() throws SQLException {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .toQuery(entityConfig);
        MysqlDriver driver = new MysqlDriver(DatabaseManager.getConnection());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }
}
