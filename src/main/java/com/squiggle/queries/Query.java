package com.squiggle.queries;

import java.util.*;

import com.squiggle.base.*;
import com.squiggle.base.Transactables.Transactable;
import com.squiggle.exceptions.NoTableException;
import com.squiggle.interfaces.Validatable;
import com.squiggle.output.*;
import com.squiggle.parsers.Parser;
import com.squiggle.parsers.Parserable;

public abstract class Query extends Parserable implements Validatable, Transactable {

    public static final int indentSize = 4;

    protected Table baseTable;
    protected List<Column> columns;
    protected List<Criteria> criteria;

    public Query(Parser parser) {
        super(parser);
        this.baseTable = null;
        this.columns = new LinkedList<>();
        this.criteria = new LinkedList<>();
    }

    public Query() {
        super();
        this.baseTable = null;
        this.columns = new LinkedList<>();
        this.criteria = new LinkedList<>();
    }

    public String toString() {
        validate();
        validateMain();
        return ToStringer.toString(this);
    }

    protected void validateMain() {
        if (this.baseTable == null) {
            throw new NoTableException("Cannot make query without table");
        }
    }

    public Table getBaseTable() {
        return baseTable;
    }

    public List<Column> listColumns() {
        return Collections.unmodifiableList(columns);
    }

    public List<Criteria> listCriteria() {
        return Collections.unmodifiableList(criteria);
    }

}
