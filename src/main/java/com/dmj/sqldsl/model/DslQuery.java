package com.dmj.sqldsl.model;

import com.dmj.sqldsl.model.condition.Conditions;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DslQuery {
    private Select select;
    private Conditions conditions;
}
