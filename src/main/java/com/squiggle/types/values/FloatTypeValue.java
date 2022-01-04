package com.squiggle.types.values;

import com.squiggle.output.Output;

public class FloatTypeValue implements TypeValue {

    Float floatValue;

    public FloatTypeValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    @Override
    public void write(Output out) {
        String str = String.format("%f", this.floatValue);
        out.print(str);

    }

}
