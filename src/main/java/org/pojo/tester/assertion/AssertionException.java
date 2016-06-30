package org.pojo.tester.assertion;


public class AssertionException extends RuntimeException {

    AssertionException(final String message) {
        super(message);
        setStackTrace(new StackTraceElement[]{});
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
