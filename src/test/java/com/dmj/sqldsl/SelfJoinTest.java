package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.table.EntityBuilder;
import com.dmj.sqldsl.entity.Dept;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.List;
import org.junit.jupiter.api.TestTemplate;

@Database
public class SelfJoinTest extends DatabaseTest {

  @TestTemplate
  public void should_return_up_dept_when_select_given_other_alias_method() {
    EntityBuilder selfDept = EntityBuilder.alias(Dept.class);
    DslQuery query = DslQueryBuilder
        .selectAll(selfDept)
        .from(Dept.class)
        .leftJoin(selfDept, eq(Dept::getParent, selfDept.col(Dept::getId)))
        .where(eq(Dept::getName, "Development Department"))
        .toQuery();

    List<Dept> actual = executor.execute(query, Dept.class);

    List<Dept> expected = singletonList(
        new Dept(1, "Group headquarters", null)
    );
    assertEquals(expected, actual);
  }
}
