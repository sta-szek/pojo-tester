package pl.pojo.tester.internal.assertion.hashcode;


import java.util.List;

public class IdenticalHashCodeAssertionError extends AbstractHashCodeAssertionError {

    private static final String CONSTRAINT_IDENTICAL = "The hashCode method should not return one hash code for every object.\n"
            + "Current implementation returns the same hash code for all tested non equal objects.\n"
            + "Object:\n"
            + "%s\n"
            + "and\n"
            + "%s\n"
            + "other non equal objects all have hash code:\n"
            + "%s";
    private final Object testedObject;
    private final int otherObjectsCount;
    private final int hashCode;

    IdenticalHashCodeAssertionError(final Class<?> testedCass,
                                    final Object testedObject,
                                    final List<Object> otherObjects,
                                    final int hashCode) {
        super(testedCass);
        this.testedObject = testedObject;
        this.otherObjectsCount = otherObjects.size();
        this.hashCode = hashCode;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_IDENTICAL, testedObject, otherObjectsCount, hashCode);
    }
}
