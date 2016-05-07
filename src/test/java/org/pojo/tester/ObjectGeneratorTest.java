package org.pojo.tester;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import test.GoodPojo_Equals_HashCode_ToString;
import test.TestHelper;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FieldUtils.class, ObjectGenerator.class})
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
        final Object result = objectGenerator.createInstanceWithDifferentFieldValues(goodPojo, allFields);

        // then
        assertThat(result).isNotEqualTo(goodPojo);
    }

    @Test
    public void shouldThrowExceptionWhenCannotCreateInstance() {
        //given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Throwable result = catchThrowable(() -> objectGenerator.createNewInstance(List.class));

        // then
        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }


}
