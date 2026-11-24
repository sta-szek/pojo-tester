package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThatCode;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class FinalFieldDefaultPathTest {

    @Test
    void Should_Test_Equals_And_Hash_Code_Without_Writing_A_Final_Field() {
        assertThatCode(() -> assertPojoMethodsFor(WithFinal.class).testing(EQUALS, HASH_CODE).areWellImplemented())
                .doesNotThrowAnyException();
    }

    static class WithFinal {
        private final String id = "fixed";
        private String name;

        @Override
        public boolean equals(final Object other) {
            requireUntouched();
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final WithFinal that = (WithFinal) other;
            that.requireUntouched();
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            requireUntouched();
            return Objects.hash(name);
        }

        private void requireUntouched() {
            if (!"fixed".equals(id)) {
                throw new IllegalStateException("final field was changed: " + id);
            }
        }
    }
}
