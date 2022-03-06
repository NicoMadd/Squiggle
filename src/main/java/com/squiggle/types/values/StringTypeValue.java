package com.squiggle.types.values;

import com.squiggle.output.Output;

public class StringTypeValue implements TypeValue {

    String stringValue;
    Boolean withQuotes;

    public StringTypeValue(String stringValue) {
        this.stringValue = stringValue;
        this.withQuotes = true;
    }

    public StringTypeValue(String stringValue, Boolean withQuotes) {
        this.stringValue = stringValue;
        this.withQuotes = withQuotes;

    }

    @Override
    public void write(Output out) {
        String str = withQuotes ? String.format("'%s'", this.stringValue) : this.stringValue;
        out.print(str);
    }

}
