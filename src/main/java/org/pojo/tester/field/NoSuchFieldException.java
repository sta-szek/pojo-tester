package org.pojo.tester.field;


public class NoSuchFieldException extends RuntimeException {

    public NoSuchFieldException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
