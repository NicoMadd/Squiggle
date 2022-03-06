package com.squiggle.queries;

import com.squiggle.interfaces.Validatable;
import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parserable;

public class CreateDatabaseQuery extends Parserable implements Validatable {

    String databaseName;

    public CreateDatabaseQuery(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public void validate() {
        if (this.databaseName == null) {
            throw new IllegalArgumentException("Cannot create database without a name");
        }

    }

    @Override
    public void write(Output out) {
        validate();
        this.parser.createDatabase(out, this);

    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

}
