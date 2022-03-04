package com.squiggle.queries;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.squiggle.base.*;
import com.squiggle.exceptions.NoColumnsException;
import com.squiggle.output.*;
import com.squiggle.types.values.DateTypeValue;
import com.squiggle.types.values.DoubleTypeValue;
import com.squiggle.types.values.FloatTypeValue;
import com.squiggle.types.values.IntegerTypeValue;
import com.squiggle.types.values.StringTypeValue;
import com.squiggle.types.values.TypeValue;

/**
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author nmadeo - Nicolas Madeo
 */

// TODO Refactor a algo mas generico con las rows y los values. Las que estan en
// update y las de insert
// se tendria que poder relacion una columna con uno o muchos valores.
public class UpdateQuery extends Query {

    HashMap<Column, TypeValue> values;
    Column lastColumn;

    public UpdateQuery() {
        super();
        this.values = new LinkedHashMap<Column, TypeValue>();
        this.lastColumn = null;

    }

    public UpdateQuery table(Table table) {
        this.baseTable = table;
        return this;
    }

    public UpdateQuery table(String tableName, String alias) {
        this.baseTable = new Table(tableName, alias);
        return this;
    }

    public UpdateQuery table(String tableName) {
        this.baseTable = new Table(tableName);
        return this;
    }

    public UpdateQuery set(String column) {
        this.lastColumn = this.baseTable.getColumn(column);
        this.columns.add(this.lastColumn);
        return this;
    }

    public UpdateQuery set(Column column) {
        this.lastColumn = column;
        this.columns.add(column);
        return this;
    }

    public UpdateQuery to(Date date) {
        this.values.put(this.lastColumn, new DateTypeValue(date));
        return this;
    }

    public UpdateQuery to(String string) {
        this.values.put(this.lastColumn, new StringTypeValue(string));
        return this;
    }

    public UpdateQuery to(Integer integer) {
        this.values.put(this.lastColumn, new IntegerTypeValue(integer));
        return this;
    }

    public UpdateQuery to(Float floatValue) {
        this.values.put(this.lastColumn, new FloatTypeValue(floatValue));
        return this;
    }

    public UpdateQuery to(Double doubleValue) {
        this.values.put(this.lastColumn, new DoubleTypeValue(doubleValue));
        return this;
    }

    public UpdateQuery where(String columnName, Function<CriteriaBuilder, CriteriaBuilder> condition) {
        CriteriaBuilder criteriaBuilder = new CriteriaBuilder(new Column(this.baseTable, columnName));
        return condition.apply(criteriaBuilder).build().getClass() == NoCriteria.class ? this
                : this.addCriteria(condition.apply(criteriaBuilder).build());
    }

    private UpdateQuery addCriteria(Criteria criteria) {
        this.criteria.add(criteria);
        return this;
    }

    public void write(Output out) {
        validate();
        this.parser.updateQuery(this, out);
    }

    @Override
    public void validate() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void validateMain() {
        super.validateMain();
        if (this.columns.size() == 0) {
            throw new NoColumnsException("Cannot make query without related column");
        }
    }

    @Override
    public List<Table> getUsedTables() {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<Entry<? extends Outputable, ? extends Outputable>> getEntries() {
        return this.values.entrySet().stream().collect(Collectors.toList());
    }
}
