package com.squiggle.base;

import com.squiggle.builders.LogicBuilder;
import com.squiggle.output.Output;

public class WhereCondition extends Criteria {

    LogicBuilder logic;

    public WhereCondition(LogicBuilder logicBuilder, Table table) {
        this.logic = logicBuilder;
    }

    @Override
    public void write(Output out) {
        out.print(logic.toString());
    }

}
