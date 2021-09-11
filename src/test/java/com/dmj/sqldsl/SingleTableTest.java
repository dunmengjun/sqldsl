package com.dmj.sqldsl;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.driver.MysqlDriver;
import com.dmj.sqldsl.entity.User;
import com.dmj.sqldsl.model.DslQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleTableTest {

    private final MysqlDriver driver = new MysqlDriver(new DatabaseManager());

    @BeforeAll
    static void beforeAll() {
        DatabaseManager.executeSqlFile(SingleTableTest.class.getSimpleName());
    }

    @AfterAll
    static void afterAll() {
        DatabaseManager.cleanDatabase();
    }

    @Test
    public void should_return_all_user_when_select_all_given_no_condition() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = Arrays.asList(
                new User(1, "alice", 16),
                new User(2, "bob", 17),
                new User(3, "tom", 17)
        );
        assertEquals(expected, result);
    }

    @Test
    public void should_return_alice_user_when_select_by_id_given_id_is_1() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getId, 1))
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = singletonList(new User(1, "alice", 16));
        assertEquals(expected, result);
    }

    @Test
    public void should_return_bob_user_when_select_by_name_given_name_is_bob() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getName, "bob"))
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = singletonList(new User(2, "bob", 17));
        assertEquals(expected, result);
    }

    @Test
    public void should_return_two_user_when_select_by_or_condition_given_id_is_1_or_2() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getId, 1)
                        .or()
                        .eq(User::getId, 2))
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = Arrays.asList(
                new User(1, "alice", 16),
                new User(2, "bob", 17)
        );
        assertEquals(expected, result);
    }

    @Test
    public void should_return_two_user_when_select_by_nested_or_condition_given_nested_condition() {
        DslQuery query = DslQueryBuilder
                .selectAll(User.class)
                .from(User.class)
                .where(new ConditionsBuilder()
                        .eq(User::getId, 1)
                        .or(x -> x.eq(User::getAge, 17).eq(User::getName, "tom")))
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = Arrays.asList(
                new User(1, "alice", 16),
                new User(3, "tom", 17)
        );
        assertEquals(expected, result);
    }

    @Test
    public void should_return_one_user_with_select_fields_when_select_fields() {
        DslQuery query = DslQueryBuilder
                .select(User::getId, User::getName)
                .from(User.class)
                .where(new ConditionsBuilder().eq(User::getId, 1))
                .toQuery();

        List<User> result = driver.execute(query, User.class);

        List<User> expected = singletonList(
                new User(1, "alice", null)
        );
        assertEquals(expected, result);
    }
}
