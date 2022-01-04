package com.squiggle.builders;

import com.squiggle.base.ColumnDef;
import com.squiggle.types.definitions.TypeDef;

public class ColumnDefBuilder {

    public static ColumnDefBuilder create(String name, TypeDef type) {
        return new ColumnDefBuilder(name, type);
    }

    private String name;
    private TypeDef type;

    private ColumnDefBuilder(String name, TypeDef type) {
        this.name = name;
        this.type = type;
    }

    public ColumnDefBuilder() {
        this.name = null;
        this.type = null;
    }

    public ColumnDefBuilder name(String name) {
        if (this.name != null) {
            throw new IllegalStateException("Column name already set");
        }
        this.name = name;
        return this;
    }

    public ColumnDefBuilder type(TypeDef type) {
        if (this.type != null) {
            throw new IllegalStateException("Column type already set");
        }
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
        return new ColumnDef(name, type);
    }

    public ColumnDefBuilder reset() {
        this.name = null;
        this.type = null;
        return this;
    }

}