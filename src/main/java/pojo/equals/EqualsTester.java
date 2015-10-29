package pojo.equals;

import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.field.primitive.PrimitiveValueChanger;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EqualsTester {

    //Whenever a.equals(b), then a.hashCode() must be same as b.hashCode().
    private static final String CONSTRAINT_NULL = "%s has bad 'equals' method implementation. The equals method should return false if object is comparing to null. Current implementation returns true.";
    private static final String CONSTRAINT_OTHER_TYPE = "%s has bad 'equals' method implementation. The equals method should return false if object is comparing to object with different type. Current implementation returns true.";
    private static final String CONSTRAINT_SAME_OBJECT = "%s has bad 'equals' method implementation. The equals method should return true if object is comparing with itself. Current implementation returns false.";
    private static final String CONSTRAINT_SAME_OBJECT_TYPE = "%s has bad 'equals' method implementation. The equals method should return true for both a.equals(b) and b.equals(a). Current implementation returns false.";
    private static final String CONSTRAINT_A_B_C = "%s has bad 'equals' method implementation. The equals method should return true in all cases: a.equals(b), b.equals(c) and a.equals(c) . Current implementation returns false.";
    private static final String CONSTRAINT_SAME_OBJECT_DIFFERENT_VALUES = "%s has bad 'equals' method implementation. The equals method should return true if object is comparing with another object that has same field values. Current implementation returns false.";

    private FieldsValuesChanger fieldsValuesChanger;
    private TestResult.Builder testResultBuilder;

    public EqualsTester() {
        try {
            fieldsValuesChanger = PrimitiveValueChanger.instance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public TestResult testEquals(Class... classes) {
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
    private boolean shouldNotEqualNull(Object object) {
        // given

        // when
        boolean result = object.equals(null);

        // then
        checkConstraint(result, object, CONSTRAINT_NULL);
        return !result;
    }

    private boolean shouldNotEqualObjectWithDifferentType(Object object) {
        // given
        Object objectToCompare = this;

        // when
        boolean result = object.equals(objectToCompare);

        // then
        checkConstraint(result, object, CONSTRAINT_OTHER_TYPE);
        return !result;
    }

    //object.equals(object) == true
    private boolean shouldEqualSameObject(Object object) {
        // given

        // when
        boolean result = object.equals(object);

        // then
        checkConstraint(!result, object, CONSTRAINT_SAME_OBJECT);
        return result;
    }

    //object.equals(other) == other.equals(object)
    private boolean shouldEqualDifferentObjectWithSameType(Object object) {
        // given
        Object objectToCompare = createInstance(object.getClass());

        // when
        boolean result1 = object.equals(objectToCompare);
        boolean result2 = objectToCompare.equals(object);

        // then
        boolean totalResult = result1 && result2;
        checkConstraint(!totalResult, object, CONSTRAINT_SAME_OBJECT_TYPE);
        return totalResult;
    }

    //a.equals(b) == true && b.equals(c) == true => a.equals(c) == true.
    private boolean shouldEqualObjectCifObjectBisEqualToObjectAndC(Object object) {
        // given
        Object b = createInstance(object.getClass());
        Object c = createInstance(object.getClass());

        // when
        boolean result1 = object.equals(b);
        boolean result2 = b.equals(c);
        boolean result3 = object.equals(c);

        // then
        boolean totalResult = result1 && result2 && result3;
        checkConstraint(!totalResult, object, CONSTRAINT_A_B_C);
        return totalResult;
    }

    private boolean shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues(Object object) {
        // given
        Object objectToCompare = createInstanceWithDifferentFieldValues(object);

        // when
        boolean result = object.equals(objectToCompare);

        // then
        checkConstraint(result, object, CONSTRAINT_SAME_OBJECT_DIFFERENT_VALUES);
        return !result;
    }

    private Object createInstance(Class clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new AssertionError("Unable to create object for class: " + clazz);
        }
        return object;
    }

    private Object createInstanceWithDifferentFieldValues(Object object) {
        Object otherObject = createInstance(object.getClass());

        if (fieldsValuesChanger != null) {
            fieldsValuesChanger.changeFieldsValues(object, otherObject);
        }

        return otherObject;
    }

    private void checkConstraint(boolean result, Object object, String message) {
        Class clazz = object.getClass();
        if (result) {
            testResultBuilder.fail(clazz);
            testResultBuilder.message(clazz, String.format(message, clazz));
        }
    }

    private Object passTest(Object object) {
        testResultBuilder.pass(object.getClass());
        return object;
    }
}