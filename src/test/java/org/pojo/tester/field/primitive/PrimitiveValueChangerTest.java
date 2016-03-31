package org.pojo.tester.field.primitive;


import org.junit.Test;
import org.pojo.tester.field.FieldsValuesChanger;

import static org.assertj.core.api.Assertions.assertThat;

public class PrimitiveValueChangerTest {

    @Test
    public void shouldCreateInstanceWithPrimitiveChangers() throws InstantiationException, IllegalAccessException {
        // given

        // when
        FieldsValuesChanger fieldsValuesChanger = PrimitiveValueChanger.instance();

        // then
        assertThat(fieldsValuesChanger).isNotNull();
    }

}