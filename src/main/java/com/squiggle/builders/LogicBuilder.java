package com.squiggle.builders;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.base.Logic.AndRelation;
import com.squiggle.base.Logic.EqualRelation;
import com.squiggle.base.Logic.Expression;
import com.squiggle.base.Logic.NotEqualRelation;
import com.squiggle.base.Logic.OrRelation;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.output.ToStringer;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.DoubleTypeValue;
import com.squiggle.types.values.FloatTypeValue;
import com.squiggle.types.values.IntegerTypeValue;
import com.squiggle.types.values.OutputableTypeValue;
import com.squiggle.types.values.StringTypeValue;
import com.squiggle.types.values.TypeValue;

public class LogicBuilder implements Outputable {

    private List<Expression> expressions;
    private Expression actualExpression;
    private TypeValue that;

    public LogicBuilder() {
        this.expressions = new LinkedList<>();
        this.actualExpression = null;
        this.that = null;
    }

    public LogicBuilder that(String arg) {
        this.that = new StringTypeValue(arg);
        return this;
    }

    public LogicBuilder that(Integer arg) {
        this.that = new IntegerTypeValue(arg);
        return this;
    }

    public LogicBuilder that(Float arg) {
        this.that = new FloatTypeValue(arg);
        return this;
    }

    public LogicBuilder that(Double arg) {
        this.that = new DoubleTypeValue(arg);
        return this;
    }

    public LogicBuilder that(Boolean arg) {
        this.that = new BooleanTypeValue(arg);
        return this;
    }

    public LogicBuilder that(Outputable outputable) {
        this.that = new OutputableTypeValue(outputable);
        return this;
    }

    public LogicBuilder equals(String arg) {
        this.actualExpression = new EqualRelation(this.that, new StringTypeValue(arg));
        return this;
    }

    public LogicBuilder equals(Integer arg) {
        this.actualExpression = new EqualRelation(this.that, new IntegerTypeValue(arg));
        return this;
    }

    public LogicBuilder equals(Float arg) {
        this.actualExpression = new EqualRelation(this.that, new FloatTypeValue(arg));
        return this;
    }

    public LogicBuilder equals(Double arg) {
        this.actualExpression = new EqualRelation(this.that, new DoubleTypeValue(arg));
        return this;
    }

    public LogicBuilder equals(Boolean arg) {
        this.actualExpression = new EqualRelation(this.that, new BooleanTypeValue(arg));
        return this;
    }

    public LogicBuilder equals(Outputable arg) {
        this.actualExpression = new EqualRelation(this.that, new OutputableTypeValue(arg));
        return this;
    }

    public LogicBuilder not(String arg) {
        this.actualExpression = new NotEqualRelation(this.that, new StringTypeValue(arg));
        return this;
    }

    public LogicBuilder not(Integer arg) {
        this.actualExpression = new NotEqualRelation(this.that, new IntegerTypeValue(arg));
        return this;
    }

    public LogicBuilder not(Float arg) {
        this.actualExpression = new NotEqualRelation(this.that, new FloatTypeValue(arg));
        return this;
    }

    public LogicBuilder not(Double arg) {
        this.actualExpression = new NotEqualRelation(this.that, new DoubleTypeValue(arg));
        return this;
    }

    public LogicBuilder not(Boolean arg) {
        this.actualExpression = new NotEqualRelation(this.that, new BooleanTypeValue(arg));
        return this;
    }

    public LogicBuilder not(Outputable arg) {
        this.actualExpression = new NotEqualRelation(this.that, new OutputableTypeValue(arg));
        return this;
    }

    private void validateAnd() {
    }

    public LogicBuilder and(String arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new StringTypeValue(arg);
        return this;
    }

    public LogicBuilder and(Integer arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new IntegerTypeValue(arg);
        return this;
    }

    public LogicBuilder and(Float arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new FloatTypeValue(arg);
        return this;
    }

    public LogicBuilder and(Double arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new DoubleTypeValue(arg);
        return this;
    }

    public LogicBuilder and(Boolean arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new BooleanTypeValue(arg);
        return this;
    }

    public LogicBuilder and(Outputable arg) {
        validateAnd();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new AndRelation());
        this.actualExpression = null;
        this.that = new OutputableTypeValue(arg);
        return this;
    }

    private void validateOr() {
    }

    public LogicBuilder or(String arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new StringTypeValue(arg);
        return this;
    }

    public LogicBuilder or(Integer arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new IntegerTypeValue(arg);
        return this;
    }

    public LogicBuilder or(Float arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new FloatTypeValue(arg);
        return this;
    }

    public LogicBuilder or(Double arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new DoubleTypeValue(arg);
        return this;
    }

    public LogicBuilder or(Boolean arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new BooleanTypeValue(arg);
        return this;
    }

    public LogicBuilder or(Outputable arg) {
        validateOr();
        this.expressions.add(this.actualExpression);
        this.expressions.add(new OrRelation());
        this.actualExpression = null;
        this.that = new OutputableTypeValue(arg);
        return this;
    }

    public String toString() {
        return ToStringer.toString(this);
    }

    @Override
    public void write(Output out) {
        if (this.actualExpression != null) {
            this.expressions.add(this.actualExpression);
        }

        for (Expression e : this.expressions) {
            e.write(out);
        }
    }

}
