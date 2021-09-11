package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Select {

  private List<Column> columns;
  private List<Table> tables;
}
