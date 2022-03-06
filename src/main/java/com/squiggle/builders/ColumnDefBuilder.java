package com.squiggle.builders;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.squiggle.base.ColumnDef;
import com.squiggle.constraints.AutoIncrement;
import com.squiggle.constraints.Constraint;
import com.squiggle.constraints.DefaultValue;
import com.squiggle.constraints.ForeignKey;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.types.definitions.TypeDef;
import com.squiggle.types.values.BooleanTypeValue;
import com.squiggle.types.values.DateTypeValue;
import com.squiggle.types.values.DoubleTypeValue;
import com.squiggle.types.values.IntegerTypeValue;
import com.squiggle.types.values.StringTypeValue;

public class ColumnDefBuilder {

    public static ColumnDefBuilder create(String name, TypeDef type, Boolean isPrimaryKey) {
        return new ColumnDefBuilder();
    }

    private String name;
    private TypeDef type;
    private List<Constraint> constraints;

    public ColumnDefBuilder() {
        this.name = null;
        this.type = null;
        this.constraints = new LinkedList<>();
    }

    private Boolean hasConstraint(Class constraintClass) {
        for (Constraint constraint : constraints) {
            if (constraint.getClass().equals(constraintClass)) {
                return true;
            }
        }
        return false;
    }

    public ColumnDefBuilder name(String name) {
        // if (this.name != null) {
        // throw new IllegalStateException("Column name already set");
        // }
        this.name = name;
        return this;
    }

    public ColumnDefBuilder type(TypeDef type) {
        // if (this.type != null) {
        // throw new IllegalStateException("Column type already set");
        // }
        this.type = type;
        return this;
    }

    public Boolean isComplete() {
        return this.hasName() && this.hasType();

    }

    public Boolean hasName() {
        return this.name != null;
    }

    public Boolean hasType() {
        return this.type != null;
    }

    public ColumnDef build() {
        return new ColumnDef(name, type, constraints);
    }

    public ColumnDefBuilder reset() {
        this.name = null;
        this.type = null;
        this.constraints = new LinkedList<>();
        return this;
    }

    public ColumnDefBuilder primaryKey() {
        this.constraints.add(new PrimaryKey());
        return this;
    }

    // TODO check if nullable in list of constraints else add it

    public ColumnDefBuilder notNullable() {
        if (hasConstraint(Nullable.class)) {
            System.out.println("thrown");
            throw new IllegalStateException("Column already has a nullable constraint");
        }
        this.constraints.add(new NotNullable());
        return this;
    }

    public ColumnDefBuilder nullable() {
        if (hasConstraint(NotNullable.class)) {
            throw new IllegalStateException("Column already has a not nullable constraint");
        }
        this.constraints.add(new Nullable());
        return this;
    }

    public ColumnDefBuilder unique() {
        this.constraints.add(new Unique());
        return this;
    }

    public ColumnDefBuilder foreignKey(String table, String foreignColumn) {
        this.constraints.add(new ForeignKey(name, table, foreignColumn));
        return this;
    }

    public void defaultValue(String value) {
        this.constraints.add(new DefaultValue(new StringTypeValue(value, true)));
    }

    public void defaultValue(Integer value) {
        this.constraints.add(new DefaultValue(new IntegerTypeValue(value)));
    }

    public void defaultValue(Double value) {
        this.constraints.add(new DefaultValue(new DoubleTypeValue(value)));
    }

    public void defaultValue(Boolean value) {
        this.constraints.add(new DefaultValue(new BooleanTypeValue(value)));
    }

    public void defaultValue(Date value) {
        this.constraints.add(new DefaultValue(new DateTypeValue(value)));
    }

    public void autoIncrement() {
        this.constraints.add(new AutoIncrement(1, 1));
    }

    public void autoIncrement(Integer start) {
        this.constraints.add(new AutoIncrement(start, 1));
    }

    public void autoIncrement(Integer start, Integer increment) {
        this.constraints.add(new AutoIncrement(start, increment));
    }

}