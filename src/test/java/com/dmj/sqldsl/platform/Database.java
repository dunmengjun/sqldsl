package com.dmj.sqldsl.platform;


import static com.dmj.sqldsl.platform.H2Mode.h2;
import static com.dmj.sqldsl.platform.H2Mode.mysql;
import static com.dmj.sqldsl.platform.H2Mode.oracle;
import static com.dmj.sqldsl.platform.H2Mode.postgresql;
import static com.dmj.sqldsl.platform.H2Mode.sqlserver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(DatabaseContextProvider.class)
public @interface Database {

  H2Mode[] value() default {h2, mysql, postgresql, oracle, sqlserver};
}
