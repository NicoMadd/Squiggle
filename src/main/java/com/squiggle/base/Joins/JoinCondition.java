package com.squiggle.base.Joins;

import com.squiggle.base.Table;
import com.squiggle.builders.LogicBuilder;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;

public class JoinCondition implements Outputable {

    LogicBuilder logic;
    Table table;

    public JoinCondition(LogicBuilder logicBuilder, Table table) {
        this.logic = logicBuilder;
        this.table = table;
    }

    public Table getTable(){
        return table;
    }

    @Override
    public void write(Output out) {
        out.print(logic.toString());
    }

    
    

}
