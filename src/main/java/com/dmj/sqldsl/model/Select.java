package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.column.Column;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Select {
    private List<Column> columns;
    private List<Table> tables;
}
