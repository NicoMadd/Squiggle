package com.squiggle.types.values;

import com.squiggle.output.Output;

public class NullTypeValue implements TypeValue {

    public NullTypeValue() {
    }

    @Override
    public void write(Output out) {
        out.print("NULL");
    }

}
