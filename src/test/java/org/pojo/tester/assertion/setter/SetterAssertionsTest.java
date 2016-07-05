package org.pojo.tester.assertion.setter;


import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.GoodPojo_Equals_HashCode_ToString;
import test.settergetter.BadPojoSetterGetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class SetterAssertionsTest {


    @Test
    public void Should_Not_Throw_Exception_When_Setter_Sets_Value()
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // given
        final GoodPojo_Equals_HashCode_ToString pojo = new GoodPojo_Equals_HashCode_ToString();
        pojo.charType = 'x';
        final SetterAssertions assertions = new SetterAssertions(pojo);
        final Class<? extends GoodPojo_Equals_HashCode_ToString> pojoClass = pojo.getClass();
        final Method setter = pojoClass.getMethod("setCharType", char.class);

        // when
        final Throwable result = catchThrowable(() -> assertions.willSetValueOnField(setter, pojo.getClass()
                                                                                                 .getField("charType"), 'x'));
        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_Setter_Does_Not_Set_Value()
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // given
        final BadPojoSetterGetter pojo = new BadPojoSetterGetter();
        final SetterAssertions assertions = new SetterAssertions(pojo);
        final Class<? extends BadPojoSetterGetter> pojoClass = pojo.getClass();
        final Method setter = pojoClass.getMethod("setX", char.class);

        // when
        final Throwable result = catchThrowable(() -> assertions.willSetValueOnField(setter, pojo.getClass()
                                                                                                 .getField("charY"), 'x'));
        // then
        assertThat(result).isInstanceOf(SetterAssertionError.class);
    }
}
