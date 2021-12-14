package com.squiggle.base;

import java.util.LinkedList;
import java.util.List;

public class Row {

    List<Object> values;

    public Row() {
        values = new LinkedList<Object>();
    }

    public void addValue(Object o) {
        values.add(o);
    }

    public Integer getValuesCount() {
        return values.size();
    }

    public List<Object> getValues() {
        return this.values;
    }

}
