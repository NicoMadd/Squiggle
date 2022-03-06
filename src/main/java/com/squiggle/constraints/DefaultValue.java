package com.squiggle.constraints;

import com.squiggle.output.Output;
import com.squiggle.types.values.TypeValue;

public class DefaultValue extends Constraint {

    private TypeValue value;

    public DefaultValue(TypeValue value) {
        super();
        this.value = value;
    }

    @Override
    public void write(Output out) {
        this.parser.defaultValue(out, this);
    }

    public TypeValue getValue() {
        return value;
    }

}
