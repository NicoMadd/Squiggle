package com.squiggle.types.values;

import java.math.BigDecimal;

import com.squiggle.output.Output;

public class BigDecimalTypeValue implements TypeValue {

    BigDecimal bigDecimalValue;

    public BigDecimalTypeValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.bigDecimalValue);

    }

}
