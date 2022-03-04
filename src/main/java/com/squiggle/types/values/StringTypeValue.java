package com.squiggle.types.values;

import com.squiggle.output.Output;

public class StringTypeValue implements TypeValue {

    String stringValue;

    public StringTypeValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public void write(Output out) {
        String str = String.format("'%s'", this.stringValue);
        out.print(str);

    }

}
