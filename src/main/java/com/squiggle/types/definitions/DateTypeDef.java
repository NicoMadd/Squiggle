package com.squiggle.types.definitions;

import com.squiggle.output.Output;

public class DateTypeDef implements TypeDef {

    @Override
    public void write(Output out) {
        out.print("date");
    }

}