package com.squiggle.queries;

import java.util.*;

import javax.sound.sampled.SourceDataLine;

import com.squiggle.base.*;
import com.squiggle.output.*;

public abstract class Query implements Outputable {

    public static final int indentSize = 4;

    protected Table baseTable;
    protected List columns;
    protected List criteria;

    public Query() {
        this.baseTable = null;
        this.columns = new ArrayList();
        this.criteria = new ArrayList();
    }

    public String toString() {
        validate();
        return ToStringer.toString(this);
    }

    public String toString(Boolean indent) {
        validate();
        String query = ToStringer.toString(this);
        return indent ? query.replace(" ", "").replace("\n", " ") : query;
    }

    protected void validate() {
        if (this.baseTable == null) {
            throw new IllegalStateException("Cannot make query without table");
        }
        if (this.columns.size() == 0) {
            throw new IllegalStateException("Cannot make query without related column");
        }
    }

}
