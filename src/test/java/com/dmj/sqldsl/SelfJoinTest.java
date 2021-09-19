package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.condition.ConditionBuilders.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.builder.annotation.Alias;
import com.dmj.sqldsl.entity.Dept;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Collections;
import java.util.List;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.TestTemplate;

@Database
public class SelfJoinTest extends DatabaseTest {

  @Alias
  @NoArgsConstructor
  private static class SelfDept extends Dept {

    public SelfDept(int id, String name, Integer parent) {
      super(id, name, parent);
    }
  }

  @TestTemplate
  public void should_return_up_self_dept_when_select_given_the_dept_table_self_join() {
    DslQuery query = DslQueryBuilder
        .selectAll(SelfDept.class)
        .from(Dept.class)
        .leftJoin(SelfDept.class, eq(Dept::getParent, SelfDept::getId))
        .where(eq(Dept::getName, "开发部"))
        .toQuery();

    List<SelfDept> actual = executor.execute(query, SelfDept.class);

    List<SelfDept> expected = Collections.singletonList(
        new SelfDept(1, "集团总部", null)
    );
    assertEquals(expected, actual);
  }

  @TestTemplate
  public void should_return_up_dept_when_select_given_the_dept_table_self_join() {
    DslQuery query = DslQueryBuilder
        .selectAll(SelfDept.class)
        .from(Dept.class)
        .leftJoin(SelfDept.class, eq(Dept::getParent, SelfDept::getId))
        .where(eq(Dept::getName, "开发部"))
        .toQuery();

    List<Dept> actual = executor.execute(query, Dept.class);

    List<Dept> expected = Collections.singletonList(
        new Dept(1, "集团总部", null)
    );
    assertEquals(expected, actual);
  }

}
