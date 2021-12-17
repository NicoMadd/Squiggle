package com.squiggle.exceptions;

public class NoTableException extends RuntimeException {
    public NoTableException() {
        super("Cannot make query without table");
    }

    public NoTableException(String message) {
        super(message);
    }

}
