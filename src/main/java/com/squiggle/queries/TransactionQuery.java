package com.squiggle.queries;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.base.Transactables.Commit;
import com.squiggle.base.Transactables.Rollback;
import com.squiggle.base.Transactables.Transactable;
import com.squiggle.output.Output;
import com.squiggle.parsers.Parser;
import com.squiggle.parsers.Parserable;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class TransactionQuery extends Parserable {

    List<Transactable> transactables;

    public TransactionQuery() {
        super();
        this.transactables = new LinkedList<Transactable>();
    }

    public TransactionQuery(Parser parser) {
        super(parser);
        this.transactables = new LinkedList<Transactable>();
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

}
