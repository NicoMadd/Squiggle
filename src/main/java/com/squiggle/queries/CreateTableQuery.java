package com.squiggle.queries;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.base.ColumnDef;
import com.squiggle.base.Table;
import com.squiggle.builders.ColumnDefBuilder;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.interfaces.Validatable;
import com.squiggle.output.Output;
import com.squiggle.output.Outputable;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;
import com.squiggle.types.definitions.*;

public class CreateTableQuery extends Parserable implements Outputable, Validatable {

    private List<ColumnDef> columnsDefs;
    private ColumnDefBuilder columnDefBuilder;
    private Table baseTable;

    public CreateTableQuery(String tableName) {
        super();
        this.baseTable = new Table(tableName);
        this.columnsDefs = new LinkedList<ColumnDef>();
        this.columnDefBuilder = new ColumnDefBuilder();
    }

    public Table getTable() {
        return baseTable;
    }

    public List<ColumnDef> getColumnsDefs() {
        return columnsDefs;
    }

    @Override
    public void write(Output out) {
        validate();
        this.parser.createTableQuery(out, this);
    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    public void validate() {
        if (this.columnsDefs.size() == 0) {
            throw new NoColumnsException("Cannot create table without columns");
        }
    }

    public CreateTableQuery column(String columnName) {
        this.columnDefBuilder.name(columnName);
        return this;
    }

    public CreateTableQuery varchar() {
        this.columnDefBuilder.type(new VarcharTypeDef());
        return this;
    }

    public CreateTableQuery varchar(int length) {
        this.columnDefBuilder.type(new VarcharTypeDef(length));
        return this;
    }

    public CreateTableQuery integer() {
        this.columnDefBuilder.type(new IntTypeDef());
        return this;
    }

    public CreateTableQuery floatingPoint() {
        this.columnDefBuilder.type(new FloatTypeDef());
        return this;
    }

    public CreateTableQuery date() {
        this.columnDefBuilder.type(new DateTypeDef());
        return this;
    }

    public CreateTableQuery primaryKey() {
        this.columnDefBuilder.primaryKey();
        return this;
    }

    public CreateTableQuery pk() {
        return this.primaryKey();
    }

    public CreateTableQuery foreignKey(String table, String foreignColumn) {
        this.columnDefBuilder.foreignKey(table, foreignColumn);
        return this;
    }

    public CreateTableQuery fk(String table, String foreignColumn) {
        return this.foreignKey(table, foreignColumn);
    }

    public CreateTableQuery define() {
        this.columnsDefs.add(this.columnDefBuilder.build());
        this.columnDefBuilder.reset();
        return this;
    }

    public CreateTableQuery nullable() {
        this.columnDefBuilder.nullable();
        return this;
    }

    public CreateTableQuery notNullable() {
        this.columnDefBuilder.notNullable();
        return this;
    }

    public CreateTableQuery unique() {
        this.columnDefBuilder.unique();
        return this;
    }

}
