package com.squiggle.base;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.types.Type;

public class Row {

    List<Type> values;

    public Row() {
        values = new LinkedList<Type>();
    }

    public void addValue(Type o) {
        values.add(o);
    }

    public Integer getValuesCount() {
        return values.size();
    }

    public List<Type> getValues() {
        return this.values;
    }

}
