package com.dmj.sqldsl.model.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Conditions implements ConditionElement {

    private List<ConditionElement> conditionElements;

    public static Conditions empty() {
        return new Conditions(emptyList());
    }
}
