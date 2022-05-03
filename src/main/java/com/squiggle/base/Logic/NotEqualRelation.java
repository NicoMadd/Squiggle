package com.squiggle.base.Logic;

import com.squiggle.output.Output;
import com.squiggle.types.values.TypeValue;

public class NotEqualRelation extends Expression {
    private TypeValue arg1;
    private TypeValue arg2;

    public NotEqualRelation(TypeValue arg1, TypeValue arg2) {
        super();
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public void write(Output out) {
        arg1.write(out);
        out.print(" != ");
        arg2.write(out);
    }

}
