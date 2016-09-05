package org.pojo.tester;

import java.util.function.Predicate;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AbstractTesterTest {

    @Test
    public void Should_Call_Test_With_Expected_Predicate() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;

        // when
        abstractTester.test(clazz);

        // then
        verify(abstractTester).test(eq(clazz), argThat(new StringPredicateArgumentMatcher()));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pair() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;
        final Predicate<String> predicate = string -> string.equals("a");

        // when
        abstractTester.test(clazz, predicate);

        // then
        verify(abstractTester).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(clazz, "a")));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pairs() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;

        final ClassAndFieldPredicatePair expectedParameter = new ClassAndFieldPredicatePair(clazz);

        // when
        abstractTester.testAll(clazz);

        // then
        verify(abstractTester).testAll(argThat(new RecursivelyEqualArgumentMatcher(expectedParameter)));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pairs_Two_Times() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);

        final Class<A> aClazz = A.class;
        final Class<B> bClazz = B.class;
        final ClassAndFieldPredicatePair pair1 = new ClassAndFieldPredicatePair(aClazz);
        final ClassAndFieldPredicatePair pair2 = new ClassAndFieldPredicatePair(bClazz);

        // when
        abstractTester.testAll(pair1, pair2);

        // then
        verify(abstractTester, times(1)).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")));
        verify(abstractTester, times(1)).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")));
    }

    private class StringPredicateArgumentMatcher extends ArgumentMatcher<Predicate<String>> {
        @Override
        public boolean matches(final Object argument) {
            final Predicate<String> stringPredicate = (Predicate<String>) argument;
            return stringPredicate.test("a");
        }
    }

    private class ClassAndFieldPredicatePairArgumentMatcher extends ArgumentMatcher<ClassAndFieldPredicatePair> {
        private final Class<?> clazz;
        private final String fieldName;

        public ClassAndFieldPredicatePairArgumentMatcher(final Class<?> clazz, final String fieldName) {
            this.clazz = clazz;
            this.fieldName = fieldName;
        }

        @Override
        public boolean matches(final Object argument) {
            final ClassAndFieldPredicatePair classAndFieldPredicatePair = (ClassAndFieldPredicatePair) argument;

            final boolean classesMatches = classAndFieldPredicatePair.getClazz()
                                                                     .equals(clazz);

            final boolean predicateMatches = classAndFieldPredicatePair.getFieldsPredicate()
                                                                       .test(fieldName);
            return classesMatches && predicateMatches;
        }
    }

    private class RecursivelyEqualArgumentMatcher extends ArgumentMatcher<ClassAndFieldPredicatePair> {
        private final ClassAndFieldPredicatePair expectedParameter;

        public RecursivelyEqualArgumentMatcher(final ClassAndFieldPredicatePair expectedParameter) {
            this.expectedParameter = expectedParameter;
        }

        @Override
        public boolean matches(final Object argument) {
            final ClassAndFieldPredicatePair classAndFieldPredicatePair = (ClassAndFieldPredicatePair) argument;
            assertThat(classAndFieldPredicatePair).isEqualToComparingFieldByFieldRecursively(expectedParameter);
            return true;
        }
    }

    @Data
    private class A {
        int a;
    }

    @Data
    private class B {
        int b;
    }
}
