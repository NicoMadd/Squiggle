package com.squiggle.types.values;

import java.text.DecimalFormat;

import com.squiggle.output.Output;

public class FloatTypeValue implements TypeValue {

    Float floatValue;
    static String DEFAULT_FORMAT = "#.#######";
    static String format = DEFAULT_FORMAT;
    static DecimalFormat decimalFormat = new DecimalFormat(format);

    public FloatTypeValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    @Override
    public void write(Output out) {
        // String str = String.format("%f", this.floatValue);
        String str = decimalFormat.format(floatValue);
        out.print(str);

    }

    public static void setFormat(String string) {
        format = string;
        decimalFormat = new DecimalFormat(format);
    }

    public static void defaultFormat() {
        setFormat(DEFAULT_FORMAT);
    }

}
