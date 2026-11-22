package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class FinalFieldMutationTest {

    @Test
    void Should_Leave_Final_Fields_Unchanged_By_Default() {
        assertPojoMethodsFor(KeepsFinal.class)
                .testing(EQUALS, HASH_CODE)
                .areWellImplemented();
    }

    @Test
    void Should_Notice_An_Equals_That_Ignores_A_Final_Field_When_Changing_Them_Is_Enabled() {
        final Throwable result = catchThrowable(() -> assertPojoMethodsFor(IgnoresFinal.class)
                .changingFinalFields()
                .testing(EQUALS)
                .areWellImplemented());

        assertThat(result).isNotNull();
    }

    static final class KeepsFinal {

        private final String code = "stable";
        private int count;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final KeepsFinal that = (KeepsFinal) other;
            return count == that.count && Objects.equals(code, that.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(code, count);
        }
    }

    static final class IgnoresFinal {

        private final String code = "stable";
        private int count;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return count == ((IgnoresFinal) other).count;
        }

        @Override
        public int hashCode() {
            return Objects.hash(count);
        }
    }
}
