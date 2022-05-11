package com.squiggle.types.values;

import com.squiggle.output.Output;

public class BooleanTypeValue implements TypeValue {

    static private boolean asText = false;
    Boolean boolValue;

    public BooleanTypeValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public BooleanTypeValue(String boolValue) {
        this.boolValue = Boolean.parseBoolean(boolValue);
    }

    public BooleanTypeValue(Integer boolValue) {
        this.boolValue = boolValue == 1;
    }

    @Override
    public void write(Output out) {
        if (asText) {
            out.print(boolValue.toString());
        } else {
            out.print(boolValue ? 1 : 0);
        }
    }

    static public void setAsText(boolean asText) {
        BooleanTypeValue.asText = asText;
    }

}
