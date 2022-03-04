package com.squiggle.base;

import java.util.List;

import com.squiggle.constraints.Constraint;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.types.definitions.TypeDef;

public class ColumnDef implements Outputable {

    private TypeDef type;
    private String name;
    private List<Constraint> constraints;

    public ColumnDef(String name, TypeDef type, List<Constraint> constraints) {
        this.name = name;
        this.type = type;
        this.constraints = constraints;
    }

    @Override
    public void write(Output out) {
        out.print(name);
        out.space();
        type.write(out);
        for (Constraint constraint : constraints) {
            out.space();
            constraint.write(out);
        }
    }

}
