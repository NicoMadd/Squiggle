package com.squiggle.types.definitions;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

public abstract interface TypeDef extends Outputable {

    public abstract void write(Output out);

}