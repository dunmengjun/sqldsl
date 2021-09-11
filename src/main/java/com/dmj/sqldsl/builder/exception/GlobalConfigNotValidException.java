package com.dmj.sqldsl.builder.exception;

public class GlobalConfigNotValidException extends RuntimeException {

    public GlobalConfigNotValidException() {
        super("Global config is not valid!");
    }
}
