package com.dmj.sqldsl.driver.exception;

import com.dmj.sqldsl.model.Table;
import lombok.Getter;

@Getter
public class NotSupportedTableException extends RuntimeException {

    private final Table table;

    public NotSupportedTableException(Table table) {
        super("Not supported column: " + table);
        this.table = table;
    }
}
