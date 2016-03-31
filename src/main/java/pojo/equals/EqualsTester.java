package pojo.equals;

import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.field.primitive.PrimitiveValueChanger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EqualsTester {

    //Whenever a.equals(b), then a.hashCode() must be same as b.hashCode().
    private static final String CONSTRAINT_NULL = "%s has bad 'equals' method implementation. The equals method should return false if " +
                                                  "object is comparing to null. Current implementation returns true.";
    private static final String CONSTRAINT_OTHER_TYPE = "%s has bad 'equals' method implementation. The equals method should return false" +
                                                        " if object is comparing to object with different type. Current implementation " +
                                                        "returns true.";
    private static final String CONSTRAINT_SAME_OBJECT = "%s has bad 'equals' method implementation. The equals method should return true" +
                                                         " if object is comparing with itself. Current implementation returns false.";
    private static final String CONSTRAINT_SAME_OBJECT_TYPE = "%s has bad 'equals' method implementation. The equals method should return" +
                                                              " true for both a.equals(b) and b.equals(a). Current implementation returns" +
                                                              " false.";
    private static final String CONSTRAINT_A_B_C = "%s has bad 'equals' method implementation. The equals method should return true in " +
                                                   "all cases: a.equals(b), b.equals(c) and a.equals(c) . Current implementation returns " +
                                                   "false.";
    private static final String CONSTRAINT_SAME_OBJECT_DIFFERENT_VALUES = "%s has bad 'equals' method implementation. The equals method " +
                                                                          "should return true if object is comparing with another object " +
                                                                          "that has same field values. Current implementation returns " +
                                                                          "false.";

    private FieldsValuesChanger fieldsValuesChanger;
    private TestResult.Builder testResultBuilder;

    public EqualsTester() {
        try {
            fieldsValuesChanger = PrimitiveValueChanger.instance();
            //TODO zaloguj wyjątek, pomi� test jezeli si� nie uda
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public TestResult testEquals(final Class... classes) {
        testResultBuilder = TestResult.Builder.forClasses(classes);
        Arrays.stream(classes)
              .map(this::createInstance)
              .filter(this::shouldNotEqualNull)
              .filter(this::shouldNotEqualObjectWithDifferentType)
              .filter(this::shouldEqualSameObject)
              .filter(this::shouldEqualObjectCifObjectBisEqualToObjectAndC)
              .filter(this::shouldEqualDifferentObjectWithSameType)
              .filter(this::shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues)
              .map(this::passTest)
              .collect(Collectors.toList());
        return testResultBuilder.build();
    }

    //object.equals(null) == false
    private boolean shouldNotEqualNull(final Object object) {
        // given

        // when
        final boolean result = object.equals(null);

        // then
        checkConstraint(result, object, CONSTRAINT_NULL);
        return !result;
    }

    private boolean shouldNotEqualObjectWithDifferentType(final Object object) {
        // given
        final Object objectToCompare = this;

        // when
        final boolean result = object.equals(objectToCompare);

        // then
        checkConstraint(result, object, CONSTRAINT_OTHER_TYPE);
        return !result;
    }

    //object.equals(object) == true
    private boolean shouldEqualSameObject(final Object object) {
        // given

        // when
        final boolean result = object.equals(object);

        // then
        checkConstraint(!result, object, CONSTRAINT_SAME_OBJECT);
        return result;
    }

    //object.equals(other) == other.equals(object)
    private boolean shouldEqualDifferentObjectWithSameType(final Object object) {
        // given
        final Object objectToCompare = createInstance(object.getClass());

        // when
        final boolean result1 = object.equals(objectToCompare);
        final boolean result2 = objectToCompare.equals(object);
        final boolean totalResult = result1 && result2;

        // then
        checkConstraint(!totalResult, object, CONSTRAINT_SAME_OBJECT_TYPE);
        return totalResult;
    }

    //a.equals(b) == true && b.equals(c) == true => a.equals(c) == true.
    private boolean shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        // given
        final Class<?> objectClass = object.getClass();
        final Object b = createInstance(objectClass);
        final Object c = createInstance(objectClass);

        // when
        final boolean result1 = object.equals(b);
        final boolean result2 = b.equals(c);
        final boolean result3 = object.equals(c);
        final boolean totalResult = result1 && result2 && result3;

        // then
        checkConstraint(!totalResult, object, CONSTRAINT_A_B_C);
        return totalResult;
    }

    private boolean shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues(final Object object) {
        // given
        final Object objectToCompare = createInstanceWithDifferentFieldValues(object);

        // when
        final boolean result = object.equals(objectToCompare);

        // then
        checkConstraint(result, object, CONSTRAINT_SAME_OBJECT_DIFFERENT_VALUES);
        return !result;
    }

    private Object createInstance(final Class clazz) {
        final Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            //TODO zr�b w�asny exception + obs�uz go wyzej
            throw new AssertionError("Unable to create object for class: " + clazz);
        }
        return object;
    }

    private Object createInstanceWithDifferentFieldValues(final Object object) {
        final Object otherObject = createInstance(object.getClass());

        if (fieldsValuesChanger != null) {
            fieldsValuesChanger.changeFieldsValues(object, otherObject);
        }

        return otherObject;
    }

    private void checkConstraint(final boolean result, final Object object, final String message) {
        final Class clazz = object.getClass();
        if (result) {
            testResultBuilder.fail(clazz);
            testResultBuilder.message(clazz, String.format(message, clazz));
        }
    }

    private Object passTest(final Object object) {
        testResultBuilder.pass(object.getClass());
        return object;
    }
}