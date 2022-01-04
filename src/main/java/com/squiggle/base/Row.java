package com.squiggle.base;

import java.util.LinkedList;
import java.util.List;

import com.squiggle.types.values.TypeValue;

public class Row {

    List<TypeValue> values;

    public Row() {
        values = new LinkedList<TypeValue>();
    }

    public void addValue(TypeValue o) {
        values.add(o);
    }

    public Integer getValuesCount() {
        return values.size();
    }

    public List<TypeValue> getValues() {
        return this.values;
    }

}
