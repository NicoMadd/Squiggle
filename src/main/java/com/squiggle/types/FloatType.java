package com.squiggle.types;

import com.squiggle.output.Output;

public class FloatType extends Type {

    Float floatValue;

    public FloatType(Float floatValue) {
        this.floatValue = floatValue;
    }

    @Override
    public void write(Output out) {
        String str = String.format("%f", this.floatValue);
        out.print(str);

    }

}
