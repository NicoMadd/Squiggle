package com.squiggle.queries;

import java.util.*;
import com.squiggle.base.*;
import com.squiggle.output.*;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */
public class DeleteQuery extends Query {

    public static final int indentSize = 4;

    public DeleteQuery() {
        super();

    }

    public void write(Output out) {
        this.parser.deleteQuery(out, this);
    }

    @Override
    public List<Table> getUsedTables() {
        // TODO Auto-generated method stub
        return null;
    }

}
