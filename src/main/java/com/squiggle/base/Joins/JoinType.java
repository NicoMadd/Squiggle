package com.squiggle.base.Joins;

public enum JoinType {
    INNER("INNER"), LEFT("LEFT"), RIGHT("RIGHT"), FULL("FULL"), OUTER("OUTER");

    private String type;

    JoinType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
};
