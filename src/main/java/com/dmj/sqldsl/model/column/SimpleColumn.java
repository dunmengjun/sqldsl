package com.dmj.sqldsl.model.column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SimpleColumn implements Column {

  private String tableName;
  private String name;
}
