package com.squiggle.parsers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import com.squiggle.base.AggregatedColumn;
import com.squiggle.base.Column;
import com.squiggle.base.Table;
import com.squiggle.base.Joins.JoinCriteria;
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
import com.squiggle.output.Outputable;
import com.squiggle.queries.CreateDatabaseQuery;
import com.squiggle.queries.DeleteQuery;
import com.squiggle.queries.DropDatabaseQuery;
import com.squiggle.queries.InsertQuery;
import com.squiggle.queries.SelectQuery;
import com.squiggle.queries.TransactionQuery;
import com.squiggle.queries.UpdateQuery;
import com.squiggle.queries.TableQueries.CreateTableQuery;
import com.squiggle.queries.TableQueries.DropTableQuery;
import com.squiggle.utils.TriConsumer;

public abstract class Parser {

    public Parser() {

    }

    /**
     * Iterate through a Set and execute a function on each
     * entry.
     * 
     * @param <T>
     */
    protected <T> void iterateGenericCollection(Output out, Collection<T> set,
            TriConsumer<Output, T, Boolean> consumer) {
        for (Iterator<T> i = set.iterator(); i.hasNext();)
            consumer.accept(out, i.next(), i.hasNext());
    }

    /**
     * Iterate through a Set and execute a function on each
     * entry.
     */
    protected void iterateEntryCollection(Output out, Collection<Entry<? extends Parserable, ? extends Outputable>> set,
            TriConsumer<Output, Entry<? extends Parserable, ? extends Outputable>, Boolean> consumer) {
        for (Iterator<Entry<? extends Parserable, ? extends Outputable>> i = set.iterator(); i.hasNext();)
            consumer.accept(out, i.next(), i.hasNext());
    }

    /**
     * Iterate through a Parserable Collection and execute a function on each
     * entry.
     */
    protected void iterateParserableCollection(Output out, Collection<? extends Parserable> parserables,
            TriConsumer<Output, ? super Parserable, Boolean> consumer) {
        for (Iterator<? extends Parserable> i = parserables.iterator(); i.hasNext();)
            consumer.accept(out, i.next(), i.hasNext());
    }

    /**
     * Iterate through a Collection and append all entries (using .toString()) to a
     * StringBuffer.
     */
    protected void appendList(Output out, Collection<? extends Parserable> parserables, String separator) {
        this.iterateParserableCollection(out, parserables, (output, current, hasNext) -> {
            current.write(out);
            if (hasNext) {
                out.print(separator);
                out.space();
            }
        });
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

    public abstract void join(Output out, JoinCriteria joinCriteria);

    public abstract void validateOffset(SelectQuery selectQuery);

}
