package classesForTest;


public class ClassWithSyntheticConstructor {

    private ClassWithSyntheticConstructor(final String parameter) {}

    public static class Builder {

        public ClassWithSyntheticConstructor build() {
            return new ClassWithSyntheticConstructor("test");
        }
    }
}
