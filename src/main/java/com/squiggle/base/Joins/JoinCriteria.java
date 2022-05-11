package com.squiggle.base.Joins;

import com.squiggle.base.Column;
import com.squiggle.base.Criteria;
import com.squiggle.base.Table;
import com.squiggle.output.Output;


/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 */
public abstract class JoinCriteria extends Criteria {

    protected Column source, dest;
    protected JoinType joinType;
    protected JoinCondition joinCondition;

    public JoinCriteria(Column source, Column dest, JoinType joinType) {
        this.source = source;
        this.dest = dest;
        this.joinType = joinType;
    }

    public JoinCriteria(Column source, JoinType joinType,JoinCondition joinCondition) {
        this.source = source;
        this.joinCondition = joinCondition;
        this.joinType = joinType;
    }

    public Column getSource() {
        return source;
    }

    public Column getDest() {
        return dest;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public JoinCondition getJoinCondition() {
        return joinCondition;
    }

    public void write(Output out) {
        this.parser.join(out, this);

        //
        // out.print(source.getTable()).space().print("JOIN").space().print(dest.getTable()).space().print("ON").space()
        // .print(source).space().print("=").space().print(dest);
    }

    public Table getSourceTable() {
        return source.getTable();
    }

    public Table getDestTable() {
        if(dest != null) {
            return dest.getTable();
        }else{
            return joinCondition.getTable();
        }
    }

}
