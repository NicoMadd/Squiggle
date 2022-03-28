package com.squiggle.queries;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.squiggle.Squiggle;
import com.squiggle.base.Transactables.Commit;
import com.squiggle.base.Transactables.Rollback;
import com.squiggle.base.Transactables.Transactable;
import com.squiggle.interfaces.Validatable;
import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parser;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class TransactionQuery extends Parserable implements Validatable {

    private List<Transactable> transactables;

    public TransactionQuery() {
        super();
        this.transactables = new LinkedList<Transactable>();
    }

    public TransactionQuery(Parser parser) {
        super(parser);
        this.transactables = new LinkedList<Transactable>();
    }

    public TransactionQuery addInsert(Function<InsertQuery, InsertQuery> queryBuilder) {
        InsertQuery insert = Squiggle.Insert();
        return this.add(queryBuilder.apply(insert));
    }

    public TransactionQuery addSelect(Function<SelectQuery, SelectQuery> queryBuilder) {
        SelectQuery select = Squiggle.Select();
        return this.add(queryBuilder.apply(select));
    }

    public TransactionQuery addDelete(Function<DeleteQuery, DeleteQuery> queryBuilder) {
        DeleteQuery delete = Squiggle.Delete();
        return this.add(queryBuilder.apply(delete));
    }

    public TransactionQuery addUpdate(Function<UpdateQuery, UpdateQuery> queryBuilder) {
        UpdateQuery update = Squiggle.Update();
        return this.add(queryBuilder.apply(update));
    }

    public TransactionQuery add(Query query) {
        this.transactables.add(query);
        return this;
    }

    private TransactionQuery add(Transactable transactable) {
        this.transactables.add(transactable);
        return this;
    }

    public TransactionQuery commit() {
        return add(new Commit());
    }

    public TransactionQuery rollback() {
        return add(new Rollback());
    }

    @Override
    public void write(Output out) {
        this.parser.transaction(out, this);
    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    @Override
    public void validate() {
        if (this.transactables.size() == 0) {
            throw new IllegalStateException("Cannot make transaction without transactables");
        }

    }

    public List<Transactable> getTransactables() {
        return this.transactables;
    }

}
