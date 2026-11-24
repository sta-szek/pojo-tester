package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.assertion.hashcode.NotEqualHashCodeAssertionError;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class SameHashCodeAllowanceTest {

    @Test
    void Should_Reject_A_Constant_Hash_Code_By_Default() {
        assertThatThrownBy(() -> assertPojoMethodsFor(ConstantHash.class).testing(HASH_CODE).areWellImplemented())
                .isInstanceOf(NotEqualHashCodeAssertionError.class);
    }

    @Test
    void Should_Allow_A_Constant_Hash_Code_When_Asked() {
        assertPojoMethodsFor(ConstantHash.class)
                .testing(HASH_CODE)
                .allowingSameHashCodeForUnequalObjects()
                .areWellImplemented();
    }

    static class ConstantHash {
        private String name;
        private int age;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final ConstantHash that = (ConstantHash) other;
            return age == that.age && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
