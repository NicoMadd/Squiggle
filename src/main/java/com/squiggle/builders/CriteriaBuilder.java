/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squiggle.builders;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.squiggle.base.*;

/**
 *
 * @author nmadeo
 */
public class CriteriaBuilder {

    private Column column;
    private Criteria criteria;
    private String matchType;

    public CriteriaBuilder(Column column) {
        this.column = column;
        this.criteria = new NoCriteria();
    }

    private CriteriaBuilder addCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public CriteriaBuilder or(String value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Float value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Integer value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Boolean value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Date value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(BigDecimal value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(BigInteger value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Long value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(String value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));

    }

    public CriteriaBuilder and(Float value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(Integer value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(Boolean value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(Date value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(BigDecimal value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(BigInteger value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(Long value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder equals(String value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(Float value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(Integer value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(Boolean value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(Date value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(BigDecimal value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder equals(BigInteger value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder equals(Long value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greater(String value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greater(Float value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greater(Integer value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greater(Boolean value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greater(Date value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greater(BigDecimal value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greater(BigInteger value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greater(Long value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greaterEqual(String value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greaterEqual(Float value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greaterEqual(Integer value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greaterEqual(Boolean value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greaterEqual(Date value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder greaterEqual(BigDecimal value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greaterEqual(BigInteger value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder greaterEqual(Long value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(String value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder less(Float value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder less(Integer value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(Boolean value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(Date value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(BigDecimal value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(BigInteger value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder less(Long value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(String value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder lessEqual(Float value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder lessEqual(Integer value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(Boolean value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(Date value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(BigDecimal value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(BigInteger value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder lessEqual(Long value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder like(String value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder like(Float value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder like(Integer value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder like(Boolean value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder like(Date value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder like(BigDecimal value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder like(BigInteger value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder like(Long value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(String value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder notEqual(Float value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(Integer value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(Boolean value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(Date value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));

    }

    public CriteriaBuilder notEqual(BigDecimal value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(Long value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder notEqual(BigInteger value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, this.matchType, value));
    }

    public CriteriaBuilder link(String srcTable, String dstTable, String linkedColumn) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(
                new MatchCriteria(new Table(srcTable).getColumn(this.column.getName()),
                        MatchCriteria.EQUALS,
                        dstTable + "." + linkedColumn, false));
    }

    public Criteria build() {
        return this.criteria;
    }

}
