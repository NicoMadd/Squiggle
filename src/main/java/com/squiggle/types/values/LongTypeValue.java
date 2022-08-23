package com.squiggle.types.values;

import com.squiggle.output.Output;

public class LongTypeValue implements TypeValue {

    Long longValue;

    public LongTypeValue(Long longValue) {
        this.longValue = longValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.longValue);

    }

}
