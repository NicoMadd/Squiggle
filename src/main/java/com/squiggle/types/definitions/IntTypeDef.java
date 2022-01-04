package com.squiggle.types.definitions;

import com.squiggle.output.Output;

public class IntTypeDef implements TypeDef {

    @Override
    public void write(Output out) {
        out.print("int");
    }

}