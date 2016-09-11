package pl.pojo.tester.internal.assertion.getter;


import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.GoodPojo_Equals_HashCode_ToString;
import test.settergetter.BadPojoSetterGetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class GetterAssertionsTest {


    @Test
    public void Should_Not_Throw_Exception_When_Getter_Returns_Expected_Value()
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // given
        final GoodPojo_Equals_HashCode_ToString pojo = new GoodPojo_Equals_HashCode_ToString();
        pojo.charType = 'x';
        final GetterAssertions assertions = new GetterAssertions(pojo);
        final Class<? extends GoodPojo_Equals_HashCode_ToString> pojoClass = pojo.getClass();
        final Method getter = pojoClass.getMethod("getCharType");

        // when
        final Throwable result = catchThrowable(() -> assertions.willGetValueFromField(getter, pojoClass.getField("charType")));
        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_Getter_Does_Not_Return_Expected_Value()
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        // given
        final BadPojoSetterGetter pojo = new BadPojoSetterGetter();
        final GetterAssertions assertions = new GetterAssertions(pojo);
        final Class<? extends BadPojoSetterGetter> pojoClass = pojo.getClass();
        final Method getter = pojoClass.getMethod("getX");

        // when
        final Throwable result = catchThrowable(() -> assertions.willGetValueFromField(getter, pojo.getClass()
                                                                                                   .getField("charY")));
        // then
        assertThat(result).isInstanceOf(GetterAssertionError.class);
    }
}
