/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squiggle.base;

import java.util.Date;

import com.squiggle.base.AND;

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

    public CriteriaBuilder or(float value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(int value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder or(Date value) {
        return this.addCriteria(new OR(this.criteria, new MatchCriteria(this.column, this.matchType, value)));
    }

    public CriteriaBuilder and(String value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));

    }

    public CriteriaBuilder and(float value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));

    }

    public CriteriaBuilder and(int value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));

    }

    public CriteriaBuilder and(Date value) {
        return this.addCriteria(new AND(this.criteria, new MatchCriteria(this.column, this.matchType, value)));

    }

    public CriteriaBuilder equals(String value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(float value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(int value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder equals(Date value) {
        this.matchType = MatchCriteria.EQUALS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.EQUALS, value));
    }

    public CriteriaBuilder greater(String value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATER, value));

    }

    public CriteriaBuilder greater(float value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATER, value));

    }

    public CriteriaBuilder greater(int value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATER, value));

    }

    public CriteriaBuilder greater(Date value) {
        this.matchType = MatchCriteria.GREATER;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATER, value));

    }

    public CriteriaBuilder greaterEqual(String value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATEREQUAL, value));

    }

    public CriteriaBuilder greaterEqual(float value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATEREQUAL, value));

    }

    public CriteriaBuilder greaterEqual(int value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATEREQUAL, value));

    }

    public CriteriaBuilder greaterEqual(Date value) {
        this.matchType = MatchCriteria.GREATEREQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.GREATEREQUAL, value));

    }

    public CriteriaBuilder less(String value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESS, value));

    }

    public CriteriaBuilder less(float value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESS, value));

    }

    public CriteriaBuilder less(int value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESS, value));

    }

    public CriteriaBuilder less(Date value) {
        this.matchType = MatchCriteria.LESS;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESS, value));

    }

    public CriteriaBuilder lessEqual(String value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESSEQUAL, value));

    }

    public CriteriaBuilder lessEqual(float value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESSEQUAL, value));

    }

    public CriteriaBuilder lessEqual(int value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESSEQUAL, value));

    }

    public CriteriaBuilder lessEqual(Date value) {
        this.matchType = MatchCriteria.LESSEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LESSEQUAL, value));

    }

    public CriteriaBuilder like(String value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LIKE, value));

    }

    public CriteriaBuilder like(float value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LIKE, value));

    }

    public CriteriaBuilder like(int value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LIKE, value));

    }

    public CriteriaBuilder like(Date value) {
        this.matchType = MatchCriteria.LIKE;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.LIKE, value));

    }

    public CriteriaBuilder notEqual(String value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.NOTEQUAL, value));

    }

    public CriteriaBuilder notEqual(float value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.NOTEQUAL, value));

    }

    public CriteriaBuilder notEqual(int value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.NOTEQUAL, value));

    }

    public CriteriaBuilder notEqual(Date value) {
        this.matchType = MatchCriteria.NOTEQUAL;
        return this.addCriteria(new MatchCriteria(this.column, MatchCriteria.NOTEQUAL, value));

    }

    public Criteria build() {
        return this.criteria;
    }

}