package com.squiggle.types.values;

import java.util.Date;

import com.squiggle.output.Output;

public class DateTypeValue implements TypeValue {

    Date dateValue;

    public DateTypeValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.dateValue);

    }

}
