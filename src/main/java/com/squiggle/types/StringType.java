package com.squiggle.types;

import com.squiggle.output.Output;

public class StringType extends Type {

    String stringValue;

    public StringType(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public void write(Output out) {
        String str = String.format("'%s'", this.stringValue);
        out.print(str);

    }

}
