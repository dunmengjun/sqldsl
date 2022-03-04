package com.dmj.sqldsl.entity;

import com.dmj.sqldsl.builder.column.type.ColumnLambda;
import com.dmj.sqldsl.builder.table.Modified;
import com.dmj.sqldsl.model.ModifiedFlag;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class TypeUser extends User implements Modified {

  public TypeUser(Integer id, String name, Integer age, Integer type) {
    super(id, name, age);
    this.type = type;
  }

  public TypeUser(String name, Integer age, Integer type) {
    super(name, age);
    this.type = type;
  }

  @Column
  private Integer type;

  private List<ColumnLambda<?, ?>> forceUpdatedColumns;

  private ModifiedFlag modifiedFlag;

  @SafeVarargs
  @Override
  public final <T, R> void setForceUpdatedColumns(ColumnLambda<T, R>... columns) {
    this.forceUpdatedColumns = Arrays.asList(columns);
  }
}
