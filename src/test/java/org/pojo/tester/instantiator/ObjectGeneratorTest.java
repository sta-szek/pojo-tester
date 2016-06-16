package org.pojo.tester.instantiator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import test.GoodPojo_Equals_HashCode_ToString;
import test.TestHelper;
import test.instantiator.arrays.ObjectContainingArray;
import test.instantiator.arrays.ObjectContainingIterable;
import test.instantiator.arrays.ObjectContainingIterator;
import test.instantiator.arrays.ObjectContainingStream;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ObjectGeneratorTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = DefaultFieldValueChanger.INSTANCE;

    @Test
    public void Should_Create_Any_Instance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final Class<GoodPojo_Equals_HashCode_ToString> expectedClass = GoodPojo_Equals_HashCode_ToString.class;

        // when
        final Object result = objectGenerator.createNewInstance(expectedClass);

        // then
        assertThat(result).isInstanceOf(expectedClass);
    }

    @Test
    @Parameters(method = "objectsToCreateSameInstance")
    public void Should_Create_Same_Instance(final Object objectToCreateSameInstance) {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);

        // when
        final Object result = objectGenerator.createSameInstance(objectToCreateSameInstance);

        // then
        assertThat(result).isEqualToComparingFieldByField(objectToCreateSameInstance);
    }

    @Test
    @Parameters(method = "objectsToCreateDifferentInstance")
    public void Should_Create_Different_Instance(final Object objectToCreateSameInstance) {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final List<Field> allFields = TestHelper.getAllFieldsExceptDummyJacocoField(objectToCreateSameInstance.getClass());

        // when
        final Object result = objectGenerator.createInstanceWithDifferentFieldValues(objectToCreateSameInstance, allFields);

        // then
        assertThat(result).isNotEqualTo(objectToCreateSameInstance);
    }

    private Object[] objectsToCreateSameInstance() {
        return new Object[]{
                new GoodPojo_Equals_HashCode_ToString(),
                new ObjectContainingArray(),
                };
    }

    private Object[] objectsToCreateDifferentInstance() {
        return new Object[]{
                new ObjectContainingArray(),
                new ObjectContainingIterable(),
                new ObjectContainingIterator(),
                new ObjectContainingStream(),
                new GoodPojo_Equals_HashCode_ToString(),
                };
    }

}
