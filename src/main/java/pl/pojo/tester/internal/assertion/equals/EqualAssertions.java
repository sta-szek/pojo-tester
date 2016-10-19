package pl.pojo.tester.internal.assertion.equals;


public class EqualAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public EqualAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void isReflexive() {
        final boolean result = objectUnderAssert.equals(objectUnderAssert);
        checkResult(result, new ReflexiveEqualsAssertionError(classUnderTest, objectUnderAssert));
    }

    public void isConsistent() {
        final boolean result1 = objectUnderAssert.equals(objectUnderAssert);
        final boolean result2 = objectUnderAssert.equals(objectUnderAssert);
        final boolean result = result1 && result2;
        checkResult(result, new ConsistentEqualsAssertionError(classUnderTest, objectUnderAssert, result1, result2));
    }

    public void isSymmetric(final Object otherObject) {
        final boolean result1 = objectUnderAssert.equals(otherObject);
        final boolean result2 = otherObject.equals(objectUnderAssert);
        final boolean result = result1 == result2;
        checkResult(result, new SymmetricEqualsAssertionError(classUnderTest,
                                                              objectUnderAssert,
                                                              otherObject,
                                                              result1,
                                                              result2));
    }

    public void isTransitive(final Object b, final Object c) {
        final boolean result1 = objectUnderAssert.equals(b);
        final boolean result2 = b.equals(c);
        final boolean result3 = objectUnderAssert.equals(c);
        final boolean partialResult1 = result1 == result2;
        final boolean partialResult2 = result2 == result3;
        final boolean result = partialResult1 && partialResult2;
        checkResult(result, new TransitiveEqualsAssertionError(classUnderTest,
                                                               objectUnderAssert,
                                                               b,
                                                               c,
                                                               result1,
                                                               result2,
                                                               result3));
    }

    public void isNotEqualToNull() {
        final boolean result = !objectUnderAssert.equals(null);
        checkResult(result, new NullEqualsAssertionError(classUnderTest));
    }

    public void isNotEqualToObjectWithDifferentType(final Object otherObject) {
        final boolean result = !objectUnderAssert.equals(otherObject);
        checkResult(result, new OtherTypeEqualsAssertionError(classUnderTest, objectUnderAssert, otherObject));
    }

    public void isNotEqualTo(final Object objectToCompare) {
        final boolean result = !objectUnderAssert.equals(objectToCompare);
        checkResult(result, new NotEqualEqualsAssertionError(classUnderTest, objectUnderAssert, objectToCompare));
    }

    public void isEqualTo(final Object objectToCompare) {
        final boolean result = objectUnderAssert.equals(objectToCompare);
        checkResult(result, new EqualEqualsAssertionError(classUnderTest, objectUnderAssert, objectToCompare));
    }

    private void checkResult(final boolean pass, final EqualsAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }

}
