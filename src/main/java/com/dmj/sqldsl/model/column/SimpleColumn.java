package com.dmj.sqldsl.model.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleColumn implements Column {
    private String tableName;
    private String name;
    private Class<?> type;

    @Override
    public Class<?> getType() {
        return type;
    }
}
