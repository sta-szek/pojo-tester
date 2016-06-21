package org.pojo.tester.field.collections.iterators;


import java.lang.reflect.Field;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.fields.collections.iterators.Iterators;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AbstractIteratorsFieldValueChangerTest {

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final AbstractIteratorsFieldValueChanger changer,
                                                                      final Field field,
                                                                      final boolean expectedResult) {
        // given

        // when
        final boolean result = changer.canChange(field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field iterator = Iterators.class.getDeclaredField("iterator");
        final Field iterable = Iterators.class.getDeclaredField("iterable");

        return new Object[][]{
                {new IteratorValueChanger(), iterator, true},
                {new IterableValueChanger(), iterable, true},
                };
    }
}
