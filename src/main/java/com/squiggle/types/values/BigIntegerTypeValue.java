package com.squiggle.types.values;

import java.math.BigInteger;

import com.squiggle.output.Output;

public class BigIntegerTypeValue implements TypeValue {

    BigInteger bigIntegerValue;

    public BigIntegerTypeValue(BigInteger bigIntegerValue) {
        this.bigIntegerValue = bigIntegerValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.bigIntegerValue);

    }

}
