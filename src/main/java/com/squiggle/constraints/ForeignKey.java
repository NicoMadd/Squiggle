package com.squiggle.constraints;

import com.squiggle.output.Output;

public class ForeignKey extends Constraint {

    private String columnName;
    private String tableName;
    private String foreignColumnName;

    public ForeignKey(String columnName, String tableName, String foreignColumnName) {
        super();
        this.columnName = columnName;
        this.tableName = tableName;
        this.foreignColumnName = foreignColumnName;
    }

    // Fix to the right form
    // parser should be responsible for this.
    @Override
    public void write(Output out) {
        this.parser.foreignKey(out, this);
    }

    public String getColumnName() {
        return columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

}
