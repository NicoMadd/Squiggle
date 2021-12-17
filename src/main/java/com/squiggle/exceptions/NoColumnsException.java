package com.squiggle.exceptions;

public class NoColumnsException extends RuntimeException {
    public NoColumnsException() {
        super("Cannot make query without columns");
    }

    public NoColumnsException(String message) {
        super(message);
    }

}
