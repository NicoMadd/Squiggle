package com.squiggle.types.values;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

public class OutputableTypeValue implements TypeValue {

    Outputable outputable;

    public OutputableTypeValue(Outputable outputable) {
        this.outputable = outputable;
    }

    @Override
    public void write(Output out) {
        outputable.write(out);
    }

}
