package com.squiggle.types;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

public abstract class Type implements Outputable {

    public abstract void write(Output out);

}