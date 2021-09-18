package com.dmj.sqldsl;

import static com.dmj.sqldsl.builder.column.ColumnBuilders.count;
import static com.dmj.sqldsl.builder.column.ColumnBuilders.sum;
import static com.dmj.sqldsl.platform.H2Mode.h2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dmj.sqldsl.builder.DslQueryBuilder;
import com.dmj.sqldsl.dto.AgeCount;
import com.dmj.sqldsl.dto.AgeSum;
import com.dmj.sqldsl.entity.TypeUser;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.platform.Database;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.TestTemplate;

@Database({h2})
public class GroupByTest extends DatabaseTest {

  @TestTemplate
  public void should_return_the_count_for_age_when_select_count_age_given_group_by_age() {
    DslQuery query = DslQueryBuilder
        .select(TypeUser::getAge)
        .selectAs(count(TypeUser::getType), AgeCount::getCount)
        .from(TypeUser.class)
        .groupBy(TypeUser::getAge)
        .toQuery();

    List<AgeCount> actual = executor.execute(query, AgeCount.class);
    List<AgeCount> expected = Arrays.asList(
        new AgeCount(16, 3L),
        new AgeCount(17, 2L),
        new AgeCount(19, 1L)
    );
    assertEquals(expected, actual);
  }

  @TestTemplate
  public void should_return_the_sum_for_age_when_select_sum_age_given_group_by_age() {
    DslQuery query = DslQueryBuilder
        .select(TypeUser::getType)
        .selectAs(sum(TypeUser::getAge), AgeSum::getCount)
        .from(TypeUser.class)
        .groupBy(TypeUser::getType)
        .toQuery();

    List<AgeSum> actual = executor.execute(query, AgeSum.class);
    List<AgeSum> expected = Arrays.asList(
        new AgeSum(1, 49L),
        new AgeSum(2, 17L),
        new AgeSum(3, 35L)
    );
    assertEquals(expected, actual);
  }
}
