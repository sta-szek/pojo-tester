package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.assertion.equals.AbstractEqualsAssertionError;
import pl.pojo.tester.internal.assertion.hashcode.AbstractHashCodeAssertionError;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class StaticInnerClassTest {

    @Test
    void Should_Test_A_Class_That_Holds_A_Static_Nested_Type() {
        assertPojoMethodsFor(Questionnaire.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Test_The_Static_Nested_Type_On_Its_Own() {
        assertPojoMethodsFor(Questionnaire.Answer.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Fail_When_Equals_Ignores_The_Nested_Value() {
        assertThatThrownBy(() -> assertPojoMethodsFor(EqualsIgnoresAnswer.class).testing(EQUALS).areWellImplemented())
                .isInstanceOf(AbstractEqualsAssertionError.class)
                .hasMessageContaining("should return false");
    }

    @Test
    void Should_Fail_When_HashCode_Ignores_The_Nested_Value() {
        assertThatThrownBy(() -> assertPojoMethodsFor(HashIgnoresAnswer.class).testing(HASH_CODE).areWellImplemented())
                .isInstanceOf(AbstractHashCodeAssertionError.class)
                .hasMessageContaining("different hash codes");
    }

    static class Questionnaire {
        private String id;
        private Answer answer;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Questionnaire that = (Questionnaire) other;
            return Objects.equals(id, that.id) && Objects.equals(answer, that.answer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, answer);
        }

        static class Answer {
            private String type;
            private List<String> options;

            @Override
            public boolean equals(final Object other) {
                if (this == other) {
                    return true;
                }
                if (other == null || getClass() != other.getClass()) {
                    return false;
                }
                final Answer answer = (Answer) other;
                return Objects.equals(type, answer.type) && Objects.equals(options, answer.options);
            }

            @Override
            public int hashCode() {
                return Objects.hash(type, options);
            }
        }
    }

    static class EqualsIgnoresAnswer {
        private String id;
        private Questionnaire.Answer answer;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return Objects.equals(id, ((EqualsIgnoresAnswer) other).id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    static class HashIgnoresAnswer {
        private String id;
        private Questionnaire.Answer answer;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final HashIgnoresAnswer that = (HashIgnoresAnswer) other;
            return Objects.equals(id, that.id) && Objects.equals(answer, that.answer);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
