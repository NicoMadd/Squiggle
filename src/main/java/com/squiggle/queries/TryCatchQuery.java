package com.squiggle.queries;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.squiggle.Squiggle;
import com.squiggle.base.Transactables.Commit;
import com.squiggle.base.Transactables.Rollback;
import com.squiggle.base.Transactables.Transactable;
import com.squiggle.base.Tryables.Tryable;
import com.squiggle.interfaces.Validatable;
import com.squiggle.output.Output;
import com.squiggle.output.ToStringer;
import com.squiggle.parsers.Parser;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class TryCatchQuery extends Parserable implements Validatable {

    private List<Transactable> tryTransactables;
    private List<Transactable> catchTransactables;

    public TryCatchQuery() {
        super();
        this.tryTransactables = new LinkedList<Transactable>();
    }

    public TryCatchQuery(Parser parser) {
        super(parser);
        this.tryTransactables = new LinkedList<Transactable>();
    }

    private TryCatchQuery onTry(Transactable transactable) {
        this.tryTransactables.add(transactable);
        return this;
    }

    public TryCatchQuery tryInsert(Function<InsertQuery, InsertQuery> queryBuilder) {
        InsertQuery insert = Squiggle.Insert();
        return this.onTry(queryBuilder.apply(insert));
    }

    public TryCatchQuery trySelect(Function<SelectQuery, SelectQuery> queryBuilder) {
        SelectQuery select = Squiggle.Select();
        return this.onTry(queryBuilder.apply(select));
    }

    public TryCatchQuery tryDelete(Function<DeleteQuery, DeleteQuery> queryBuilder) {
        DeleteQuery delete = Squiggle.Delete();
        return this.onTry(queryBuilder.apply(delete));
    }

    public TryCatchQuery tryUpdate(Function<UpdateQuery, UpdateQuery> queryBuilder) {
        UpdateQuery update = Squiggle.Update();
        return this.onTry(queryBuilder.apply(update));
    }

    public TryCatchQuery onCatch(Transactable transactable){
        this.tryTransactables.add(transactable);
        return this;
    }

    public TryCatchQuery catchInsert(Function<InsertQuery, InsertQuery> queryBuilder) {
        InsertQuery insert = Squiggle.Insert();
        return this.onCatch(queryBuilder.apply(insert));
    }

    public TryCatchQuery catchSelect(Function<SelectQuery, SelectQuery> queryBuilder) {
        SelectQuery select = Squiggle.Select();
        return this.onCatch(queryBuilder.apply(select));
    }

    public TryCatchQuery catchDelete(Function<DeleteQuery, DeleteQuery> queryBuilder) {
        DeleteQuery delete = Squiggle.Delete();
        return this.onCatch(queryBuilder.apply(delete));
    }

    public TryCatchQuery catchUpdate(Function<UpdateQuery, UpdateQuery> queryBuilder) {
        UpdateQuery update = Squiggle.Update();
        return this.onCatch(queryBuilder.apply(update));
    }

    @Override
    public void write(Output out) {
        this.parser.tryCatch(out, this);
    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    @Override
    public void validate() {
        if (this.tryTransactables.size() == 0) {
            throw new IllegalStateException("Cannot make try catch without transactables in the try block");
        }
        if (this.catchTransactables.size() == 0) {
            throw new IllegalStateException("Cannot make try catch without transactables in the try block");
        }

    }

    public List<Transactable> getTryTransactables() {
        return this.tryTransactables;
    }

    public List<Transactable> getCatchTransactables() {
        return this.catchTransactables;
    }

}
