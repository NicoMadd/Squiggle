package com.squiggle.types;

import com.squiggle.output.Output;

public class DoubleType extends Type {

    Double doubleValue;

    public DoubleType(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.doubleValue);

    }

}
