package com.squiggle.base;

import java.util.List;

import com.squiggle.constraints.Constraint;
import com.squiggle.constraints.PrimaryKey;
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

    public void write(Output out, Boolean withConstraints) {
        out.print(name);
        out.space();
        type.write(out);
        if (withConstraints) {
            for (Constraint constraint : constraints) {
                out.space();
                constraint.write(out);
            }
        }

    }

    public void write(Output out, List<Class> constraintClasses) {
        out.print(name);
        out.space();
        type.write(out);
        for (Constraint constraint : constraints) {
            if (!constraintClasses.contains(constraint.getClass())) {
                out.space();
                constraint.write(out);
            }
        }

    }

    @Override
    public void write(Output out) {
        write(out, true);
    }

    public Boolean isPrimaryKey() {
        for (Constraint constraint : constraints) {
            if (constraint instanceof PrimaryKey) {
                return true;
            }
        }
        return false;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public String getName() {
        return name;
    }

}
