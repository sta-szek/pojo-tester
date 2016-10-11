package classesForTest;

class UnpublicClass {
    final static private class PrivateStaticFinalNestedClass {
        final static private class PrivateStaticFinalNestedClass2 {}
    }

    final static protected class ProtectedStaticFinalNestedClass {}

    final static class PackageStaticFinalNestedClass {}

    final static public class PublicStaticFinalNestedClass {}

    static private class PrivateStaticNestedClass {}

    static protected class ProtectedStaticNestedClass {}

    static class PackageStaticNestedClass {}

    static public class PublicStaticNestedClass {}

    final private class PrivateFinalNestedClass {}

    final protected class ProtectedFinalNestedClass {}

    final class PackageFinalNestedClass {}

    final public class PublicFinalNestedClass {}

    private class PrivateNestedClass {}

    protected class ProtectedNestedClass {}

    class PackageNestedClass {}

    public class PublicNestedClass {}
}
