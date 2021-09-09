package com.dmj.sqldsl.builder.config;

public class PojoLikedStyleTranslator implements NameTranslator {

    @Override
    public String translate(String methodName) {
        return methodName.substring(3).toLowerCase();
    }
}
