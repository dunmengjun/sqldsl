package com.dmj.sqldsl.builder.condition;

import com.dmj.sqldsl.builder.column.ColumnFunction;
import com.dmj.sqldsl.builder.config.EntityConfig;
import com.dmj.sqldsl.model.condition.ConditionElement;
import com.dmj.sqldsl.model.condition.Conditions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ConditionsBuilder implements ConditionElementBuilder {

    private JunctionBuilder defaultJunction;

    private final List<ConditionElementBuilder> elementBuilders;

    public ConditionsBuilder() {
        this.defaultJunction = new AndBuilder();
        this.elementBuilders = new ArrayList<>();
    }

    public ConditionsBuilder eq(ColumnFunction<?> function, Object object) {
        this.elementBuilders.add(defaultJunction);
        this.elementBuilders.add(new ConditionBuilder(function.getColumnBuilder(), object));
        return this;
    }

    public ConditionsBuilder and() {
        this.defaultJunction = new AndBuilder();
        return this;
    }

    public ConditionsBuilder and(Consumer<ConditionsBuilder> consumer) {
        ConditionsBuilder builder = new ConditionsBuilder();
        consumer.accept(builder);
        this.elementBuilders.add(new AndBuilder());
        this.elementBuilders.add(builder);
        return this;
    }

    public ConditionsBuilder or() {
        this.defaultJunction = new OrBuilder();
        return this;
    }

    public ConditionsBuilder or(Consumer<ConditionsBuilder> consumer) {
        ConditionsBuilder builder = new ConditionsBuilder();
        consumer.accept(builder);
        this.elementBuilders.add(new OrBuilder());
        this.elementBuilders.add(builder);
        return this;
    }

    public boolean isEmpty() {
        return this.elementBuilders.isEmpty();
    }

    public boolean isSimpleCondition() {
        return this.elementBuilders.size() == 2 || this.elementBuilders.size() == 1;
    }

    @Override
    public ConditionElement build(EntityConfig config) {
        if (this.isEmpty()) {
            return Conditions.empty();
        }
        this.elementBuilders.remove(0);
        List<ConditionElement> elements = this.elementBuilders.stream()
                .flatMap(x1 -> {
                    if (x1 instanceof ConditionsBuilder) {
                        ConditionsBuilder conditionsBuilder = (ConditionsBuilder) x1;
                        if (conditionsBuilder.isSimpleCondition()) {
                            return conditionsBuilder.elementBuilders.stream();
                        }
                    }
                    return Stream.of(x1);
                })
                .map(x -> x.build(config))
                .collect(toList());
        return new Conditions(elements);
    }
}
