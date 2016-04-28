package org.pojo.tester;

import org.junit.Test;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;
import test.TestHelper;
import test.equals.GoodPojo_Equals_HashCode_ToString;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ObjectGeneratorTest {

    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger = AbstractPrimitiveValueChanger.getInstance();

    @Test
    public void shouldCreateSameInstance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
        final GoodPojo_Equals_HashCode_ToString goodPojo = new GoodPojo_Equals_HashCode_ToString();

        // when
        final Object result = objectGenerator.createSameInstance(goodPojo);

        // then
        assertThat(result).isEqualTo(goodPojo);
    }

    @Test
    public void shouldCreateDifferentInstance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
        final GoodPojo_Equals_HashCode_ToString goodPojo = new GoodPojo_Equals_HashCode_ToString();
        final List<Field> allFields = TestHelper.getAllFieldsExceptDummyJacocoField(goodPojo.getClass());

        // when
        final GoodPojo_Equals_HashCode_ToString result = objectGenerator.createInstanceWithDifferentFieldValues(goodPojo.getClass(),
                                                                                                                allFields);
        // then
        assertThat(result).isNotEqualTo(goodPojo);
    }

}