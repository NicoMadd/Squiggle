package com.squiggle.builders;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.base.ColumnDef;
import com.squiggle.constraints.Constraint;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.types.definitions.TypeDef;

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
        this.constraints.add(new NotNullable());
        return this;
    }

    public ColumnDefBuilder nullable() {
        this.constraints.add(new Nullable());
        return this;
    }

    public ColumnDefBuilder unique() {
        this.constraints.add(new Unique());
        return this;
    }

}