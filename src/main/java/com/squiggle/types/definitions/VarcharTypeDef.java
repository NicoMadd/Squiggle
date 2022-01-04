package com.squiggle.types.definitions;

import com.squiggle.output.Output;

public class VarcharTypeDef implements TypeDef {

    Integer length;

    public VarcharTypeDef(int length) {
        this.length = length;
    }

    public VarcharTypeDef() {
        this.length = 255;
    }

    @Override
    public void write(Output out) {
        out.print("varchar(" + length + ")");
    }

}