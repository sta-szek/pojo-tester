package pl.pojo.tester.internal.assertion.hashcode;


import java.util.List;

public class HashCodeAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public HashCodeAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass(); // NOSONAR - null is rejected with an NPE
    }

    public void isConsistent() {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = objectUnderAssert.hashCode();
        final boolean result = result1 == result2;
        checkResult(result, new ConsistentHashCodeAssertionError(classUnderTest, objectUnderAssert, result1, result2));
    }

    public void returnsSameValueFor(final Object otherObject) {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = otherObject.hashCode();
        final boolean result = result1 == result2;
        checkResult(result,
                    new EqualHashCodeAssertionError(classUnderTest, objectUnderAssert, otherObject, result1, result2));
    }

    public void returnsDifferentValueFor(final Object otherObject) {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = otherObject.hashCode();
        final boolean result = result1 == result2;
        checkResult(!result,
                    new NotEqualHashCodeAssertionError(classUnderTest,
                                                       objectUnderAssert,
                                                       otherObject,
                                                       result1,
                                                       result2));

    }

    public void returnsDifferentValueForAtLeastOneOf(final List<Object> otherObjects) {
        final int result = objectUnderAssert.hashCode();
        final boolean allSame = !otherObjects.isEmpty()
                && otherObjects.stream()
                               .allMatch(eachObject -> eachObject.hashCode() == result);
        checkResult(!allSame,
                    new IdenticalHashCodeAssertionError(classUnderTest,
                                                        objectUnderAssert,
                                                        otherObjects,
                                                        result));

    }

    private void checkResult(final boolean pass, final AbstractHashCodeAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }

}
