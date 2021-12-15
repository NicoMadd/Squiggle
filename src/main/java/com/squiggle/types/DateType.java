package com.squiggle.types;

import java.util.Date;

import com.squiggle.output.Output;

public class DateType extends Type {

    Date dateValue;

    public DateType(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public void write(Output out) {
        out.print(this.dateValue);

    }

}
