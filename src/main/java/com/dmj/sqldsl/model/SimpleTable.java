package com.dmj.sqldsl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleTable implements Table {
    private final String tableName;
}
