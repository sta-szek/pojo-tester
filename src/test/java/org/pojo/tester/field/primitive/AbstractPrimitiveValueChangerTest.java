package org.pojo.tester.field.primitive;


import org.junit.Test;
import org.pojo.tester.field.AbstractFieldsValuesChanger;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractPrimitiveValueChangerTest {

    @Test
    public void shouldCreateInstanceWithPrimitiveChangers() throws InstantiationException, IllegalAccessException {
        // given

        // when
        AbstractFieldsValuesChanger abstractFieldsValuesChanger = AbstractPrimitiveValueChanger.getInstance();

        // then
        assertThat(abstractFieldsValuesChanger).isNotNull();
    }

}