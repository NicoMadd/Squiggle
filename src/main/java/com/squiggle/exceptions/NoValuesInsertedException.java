package com.squiggle.exceptions;

public class NoValuesInsertedException extends RuntimeException {
    public NoValuesInsertedException() {
        super("Cannot make query without values");
    }

    public NoValuesInsertedException(String message) {
        super(message);
    }
}
