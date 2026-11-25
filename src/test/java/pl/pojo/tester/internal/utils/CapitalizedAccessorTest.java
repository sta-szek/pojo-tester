package pl.pojo.tester.internal.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class CapitalizedAccessorTest {

    @Test
    void Should_Match_GetTRow_And_SetTRow() throws NoSuchFieldException {
        final Field field = SheetRow.class.getDeclaredField("tRow");

        assertThat(MethodUtils.findGetterFor(SheetRow.class, field).getName()).isEqualTo("getTRow");
        assertThat(MethodUtils.findSetterFor(SheetRow.class, field).getName()).isEqualTo("setTRow");
    }

    private static final class SheetRow {
        private Integer tRow;

        public Integer getTRow() {
            return tRow;
        }

        public void setTRow(final Integer tRow) {
            this.tRow = tRow;
        }
    }
}
