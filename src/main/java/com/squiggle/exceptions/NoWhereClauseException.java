package com.squiggle.exceptions;

public class NoWhereClauseException extends RuntimeException {
    public NoWhereClauseException() {
        super("Cannot make query without where clause");
    }

    public NoWhereClauseException(String message) {
        super(message);
    }
}
