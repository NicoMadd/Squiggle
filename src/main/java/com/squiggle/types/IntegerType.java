package com.squiggle.types;

import com.squiggle.output.Output;

public class IntegerType extends Type {

    Integer intValue;

    public IntegerType(Integer intValue) {
        this.intValue = intValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.intValue);

    }

}
