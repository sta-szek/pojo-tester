package sample;


public class ConstructorTests {

    private final String stringField;

    private ConstructorTests(final String stringField) throws Exception {
        this.stringField = stringField;
        switch (stringField) {
            case "a":
                throw new ExceptionExtendsException();
            case "b":
                throw new ExceptionExtendsRuntimeException();
            case "c":
                throw new RuntimeException();
            case "d":
                throw new Exception();
        }
    }

    private ConstructorTests(final ConstructorTests constructorTests) throws Exception {
        this("test");
    }

    public static class NestedPublicStaticClass {}

    static class NestedStaticClass {}

    private static class NestedPrivateStaticClass {}

    public static final class Builder {

        private Builder() { }

        public static Builder builder() {return new Builder();}

        public ConstructorTests build() throws Exception {
            return new ConstructorTests("test");
        }
    }

    public class InnerPublicClass {}

    class InnerClass {}

    private class InnerPrivateClass {}

    public class ExceptionExtendsRuntimeException extends RuntimeException {}

    public class ExceptionExtendsException extends Exception {}
}
