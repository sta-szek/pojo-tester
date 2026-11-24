package pl.pojo.tester.internal.tester;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class InitializedNestedFieldEqualsTest {

    @Test
    void Should_Accept_A_Field_Initialized_At_Declaration() {
        assertPojoMethodsFor(DeclaredParent.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Accept_A_Field_Initialized_In_The_Constructor() {
        assertPojoMethodsFor(ConstructedParent.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    static class DeclaredParent {
        private Child child = new Child();

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(child, ((DeclaredParent) other).child);
        }

        @Override
        public int hashCode() {
            return Objects.hash(child);
        }
    }

    static class ConstructedParent {
        private Child child;

        ConstructedParent() {
            child = new Child();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(child, ((ConstructedParent) other).child);
        }

        @Override
        public int hashCode() {
            return Objects.hash(child);
        }
    }

    static class Child {
        private String value;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(value, ((Child) other).value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
