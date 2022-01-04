package com.squiggle.types.values;

import com.squiggle.output.Output;

public class IntegerTypeValue implements TypeValue {

    Integer intValue;

    public IntegerTypeValue(Integer intValue) {
        this.intValue = intValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.intValue);

    }

}
