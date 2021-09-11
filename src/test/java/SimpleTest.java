import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.model.DslQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleTest {

    @Test
    public void test1() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getId, 1)
                        .or(x -> x.eq(User::getAge, 23).eq(User::getName, "alice")))
                .toQuery();
        MysqlDriver driver = new MysqlDriver(new DatabaseManager());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }

    @Test
    public void test2() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .toQuery();
        MysqlDriver driver = new MysqlDriver(new DatabaseManager());

        List<User> resultList = driver.execute(query, User.class);

        System.out.println(resultList);
    }
}
