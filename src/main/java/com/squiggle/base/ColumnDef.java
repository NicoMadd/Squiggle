package com.squiggle.base;

import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.types.definitions.TypeDef;

public class ColumnDef implements Outputable {

    private TypeDef type;
    private String name;

    public ColumnDef(String name, TypeDef type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void write(Output out) {
        out.print(name);
        out.space();
        type.write(out);

    }

}
