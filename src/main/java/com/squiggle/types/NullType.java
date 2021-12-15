package com.squiggle.types;

import com.squiggle.output.Output;

public class NullType extends Type {

    public NullType() {
    }

    @Override
    public void write(Output out) {
        out.print("NULL");
    }

}
