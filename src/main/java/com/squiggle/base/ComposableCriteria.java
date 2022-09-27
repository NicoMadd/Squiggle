package com.squiggle.base;

import java.util.Arrays;
import java.util.List;

import com.squiggle.output.Output;

public class ComposableCriteria extends Criteria {

    List<Criteria> criterias;

    public ComposableCriteria(String separator, Criteria[] criterias) {
        this.separator = separator;
        this.criterias = Arrays.asList(criterias);
    }

    @Override
    public void write(Output out) {
        out.print("(");
        for (int i = 0; i < criterias.size(); i++) {
            if (i > 0) {
                out.print(getSeparator());
            }
            criterias.get(i).write(out);
        }
        out.print(")");
    }

}
