package com.squiggle.types.values;

import com.squiggle.output.Output;

public class DoubleTypeValue implements TypeValue {

    Double doubleValue;

    public DoubleTypeValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.doubleValue);

    }

}
