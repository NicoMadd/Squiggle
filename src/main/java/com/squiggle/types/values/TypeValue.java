package com.squiggle.types.values;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

public abstract interface TypeValue extends Outputable {

    public abstract void write(Output out);

}