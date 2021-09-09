package com.dmj.sqldsl.builder;

import com.dmj.sqldsl.builder.condition.ConditionsBuilder;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.DslQuery;
import com.dmj.sqldsl.model.condition.Conditions;

public class WhereBuilder {

    private final FromBuilder fromBuilder;
    private final ConditionsBuilder conditionsBuilder;

    public WhereBuilder(FromBuilder fromBuilder, ConditionsBuilder conditionsBuilder) {
        this.fromBuilder = fromBuilder;
        this.conditionsBuilder = conditionsBuilder;
    }

    public DslQuery toQuery(EntityConfig config) {
        return DslQuery.builder()
                .select(fromBuilder.build(config))
                .conditions((Conditions) conditionsBuilder.build(config))
                .build();
    }
}
