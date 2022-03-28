package com.squiggle.parsers;

import com.squiggle.base.AggregatedColumn;
import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Transactables.Commit;
import com.squiggle.base.Transactables.Rollback;
import com.squiggle.constraints.AutoIncrement;
import com.squiggle.constraints.DefaultValue;
import com.squiggle.constraints.ForeignKey;
import com.squiggle.constraints.NotNullable;
import com.squiggle.constraints.Nullable;
import com.squiggle.constraints.PrimaryKey;
import com.squiggle.constraints.Unique;
import com.squiggle.output.Output;
import com.squiggle.queries.CreateDatabaseQuery;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.DropDatabaseQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.TransactionQuery;
import com.squiggle.queries.UpdateQuery;
import com.squiggle.queries.TableQueries.CreateTableQuery;
import com.squiggle.queries.TableQueries.DropTableQuery;

public abstract class Parser {

    public Parser() {

    }

    public abstract void selectQuery(SelectQuery selectQuery, Output out);

    public abstract void insertQuery(InsertQuery insertQuery, Output out);

    public abstract void updateQuery(UpdateQuery updateQuery, Output out);

    public abstract void deleteQuery(Output out, DeleteQuery deleteQuery);

    public abstract void createTableQuery(Output out, CreateTableQuery createTableQuery);

    public abstract void foreignKey(Output out, ForeignKey foreignKey);

    public abstract void notNullable(Output out, NotNullable notNullable);

    public abstract void nullable(Output out, Nullable nullable);

    public abstract void primaryKey(Output out, PrimaryKey primaryKey);

    public abstract void unique(Output out, Unique unique);

    public abstract void defaultValue(Output out, DefaultValue defaultValue);

    public abstract void createDatabase(Output out, CreateDatabaseQuery createDatabaseQuery);

    public abstract void dropDatabase(Output out, DropDatabaseQuery dropDatabaseQuery);

    public abstract void autoIncrement(Output out, AutoIncrement autoIncrement);

    public abstract void dropTableQuery(Output out, DropTableQuery dropTableQuery);

    public abstract void simpleColumn(Output out, Column column);

    public abstract void aggregatedColumn(Output out, AggregatedColumn aggregatedColumn);

    public abstract void columnAlias(Output out, Column column);

    public abstract void sum(Output out, AggregatedColumn aggregatedColumn);

    public abstract void average(Output out, AggregatedColumn aggregatedColumn);

    public abstract void count(Output out, AggregatedColumn aggregatedColumn);

    public abstract void commit(Output out, Commit commit);

    public abstract void rollback(Output out, Rollback rollback);

    public abstract void transaction(Output out, TransactionQuery transactionQuery);

    public abstract void table(Output out, Table table);

}
