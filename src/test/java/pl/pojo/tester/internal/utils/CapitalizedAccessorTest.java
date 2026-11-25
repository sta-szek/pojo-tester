package pl.pojo.tester.internal.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CapitalizedAccessorTest {

    @Test
    void Should_Match_GetTRow_And_SetTRow() throws NoSuchFieldException {
        final Field field = SheetRow.class.getDeclaredField("tRow");

        assertThat(MethodUtils.findGetterFor(SheetRow.class, field).getName()).isEqualTo("getTRow");
        assertThat(MethodUtils.findSetterFor(SheetRow.class, field).getName()).isEqualTo("setTRow");
    }

    @Test
    void Should_Not_Treat_A_Shorter_Name_As_The_Capitalised_Accessor() throws NoSuchFieldException {
        final Field field = ShorterName.class.getDeclaredField("tRow");

        assertThat(catchThrowable(() -> MethodUtils.findGetterFor(ShorterName.class, field)))
                .isInstanceOf(GetterNotFoundException.class);
        assertThat(catchThrowable(() -> MethodUtils.findSetterFor(ShorterName.class, field)))
                .isInstanceOf(SetterNotFoundException.class);
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

    private static final class ShorterName {
        private Integer tRow;

        public Integer getRow() {
            return tRow;
        }

        public void setRow(final Integer tRow) {
            this.tRow = tRow;
        }
    }
}
