package pl.pojo.tester.internal.tester;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class EmptyContainerCoverageTest {

    @Test
    void Should_Tell_A_Null_Map_And_Array_From_An_Empty_One() {
        assertPojoMethodsFor(Containers.class).testing(EQUALS).areWellImplemented();
    }

    @Test
    void Should_Replace_An_Equal_Nested_Object() {
        assertPojoMethodsFor(Parent.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Keep_The_Created_Value_When_The_Changer_Returns_The_Wrong_Type() {
        assertPojoMethodsFor(OnlySet.class)
                .testing(EQUALS)
                .using(new WrongTypeChanger())
                .areWellImplemented();
    }

    @Test
    void Should_Keep_An_Empty_Stack_When_No_Changer_Can_Fill_It() {
        assertPojoMethodsFor(OnlyStack.class).testing(EQUALS).areWellImplemented();
    }

    static class Containers {
        private Map<String, String> labels;
        private String[] codes;
        private String[] tags = {"kept"};
        private Set<String> roles = Collections.singleton("admin");

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Containers that = (Containers) other;
            return Objects.equals(labels, that.labels)
                    && Objects.deepEquals(codes, that.codes)
                    && Objects.deepEquals(tags, that.tags)
                    && Objects.equals(roles, that.roles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(labels, java.util.Arrays.hashCode(codes), java.util.Arrays.hashCode(tags), roles);
        }
    }

    static class Parent {
        private Child child = new Child();

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(child, ((Parent) other).child);
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

    static class OnlySet {
        private Set<String> roles;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(roles, ((OnlySet) other).roles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(roles);
        }
    }

    static class OnlyStack {
        private Stack<String> history;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(history, ((OnlyStack) other).history);
        }

        @Override
        public int hashCode() {
            return Objects.hash(history);
        }
    }

    static class WrongTypeChanger extends AbstractFieldValueChanger<Object> {

        @Override
        protected Object increaseValue(final Object value, final Class<?> type) {
            return "nope";
        }

        @Override
        protected boolean canChange(final Class<?> type) {
            return true;
        }
    }
}
