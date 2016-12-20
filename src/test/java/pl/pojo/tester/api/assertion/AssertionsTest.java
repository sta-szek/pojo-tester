package pl.pojo.tester.api.assertion;

import classesForTest.packageFilter.next.D;
import classesForTest.packageFilter.next.E;
import helpers.ClassAndFieldPredicatePairArgumentMatcher;
import helpers.ClassNameAndFieldPredicatePairArgumentMatcher;
import lombok.Data;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.DefaultPackageFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;


public class AssertionsTest {

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class() {
        // given
        final Class<A> expectedClass = A.class;
        final String fieldName = "a";
        final ClassAndFieldPredicatePairCondition conditionToMatch = new ClassAndFieldPredicatePairCondition(
                expectedClass,
                fieldName);

        // when
        final SingleClassAssertion result = (SingleClassAssertion) Assertions.assertPojoMethodsFor(expectedClass);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result,
                                                                                           "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result,
                                                                                          "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).is(conditionToMatch);
        assertThat(classAndFieldPredicatePairs).isEmpty();
    }

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class_Name() {
        // given
        final Class<A> expectedClass = A.class;
        final String fieldName = "a";
        final String expectedClassName = expectedClass.getName();
        final ClassNameAndFieldPredicatePairCondition conditionToMatch = new ClassNameAndFieldPredicatePairCondition(
                expectedClassName,
                fieldName);

        // when
        final SingleClassAssertion result = (SingleClassAssertion) Assertions.assertPojoMethodsFor(expectedClassName);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result,
                                                                                           "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result,
                                                                                          "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).is(conditionToMatch);
        assertThat(classAndFieldPredicatePairs).isEmpty();
    }

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class_And_Field_Predicate() {
        // given
        final Class<A> expectedClass = A.class;
        final String fieldName = "a";
        final Predicate<String> predicate = name -> name.equals(fieldName);
        final String expectedClassName = expectedClass.getName();
        final ClassNameAndFieldPredicatePairCondition conditionToMatch = new ClassNameAndFieldPredicatePairCondition(
                expectedClassName,
                fieldName);

        // when
        final SingleClassAssertion result = (SingleClassAssertion) Assertions.assertPojoMethodsFor(expectedClassName,
                                                                                                   predicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result,
                                                                                           "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result,
                                                                                          "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).is(conditionToMatch);
        assertThat(classAndFieldPredicatePairs).isEmpty();
    }

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class_Name_And_Field_Predicate() {
        // given
        final Class<A> expectedClass = A.class;
        final String fieldName = "a";
        final Predicate<String> predicate = name -> name.equals(fieldName);
        final ClassAndFieldPredicatePairCondition conditionToMatch = new ClassAndFieldPredicatePairCondition(
                expectedClass,
                fieldName);

        // when
        final SingleClassAssertion result = (SingleClassAssertion) Assertions.assertPojoMethodsFor(expectedClass,
                                                                                                   predicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result,
                                                                                           "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result,
                                                                                          "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).is(conditionToMatch);
        assertThat(classAndFieldPredicatePairs).isEmpty();
    }

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class_And_Field_Predicates() {
        // given
        final Class<A> expectedClass = A.class;
        final ClassAndFieldPredicatePair expectedClassAndFieldPredicate = new ClassAndFieldPredicatePair(expectedClass);

        // when
        final SingleClassAssertion result = (SingleClassAssertion) Assertions.assertPojoMethodsFor(
                expectedClassAndFieldPredicate,
                expectedClassAndFieldPredicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result,
                                                                                           "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result,
                                                                                          "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).isEqualTo(expectedClassAndFieldPredicate);
        assertThat(classAndFieldPredicatePairs).containsExactly(expectedClassAndFieldPredicate);
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Package() {
        // given
        final DefaultPackageFilter packageFilter = DefaultPackageFilter.forPackage("classesForTest.packageFilter.next");

        // when
        final AbstractAssertion result = Assertions.assertPojoMethodsForAll(packageFilter);
        List<ClassAndFieldPredicatePair> pairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(pairs).usingRecursiveFieldByFieldElementComparator()
                         .containsExactlyInAnyOrder(new ClassAndFieldPredicatePair(D.class),
                                                    new ClassAndFieldPredicatePair(E.class));
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Classes() {
        // given
        final Class<A> expectedClass1 = A.class;
        final Class<B> expectedClass2 = B.class;

        // when
        final MultiClassAssertion result = (MultiClassAssertion) Assertions.assertPojoMethodsForAll(expectedClass1,
                                                                                                    expectedClass2);
        List<ClassAndFieldPredicatePair> pairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(pairs).usingRecursiveFieldByFieldElementComparator()
                         .containsExactlyInAnyOrder(new ClassAndFieldPredicatePair(A.class),
                                                    new ClassAndFieldPredicatePair(B.class));
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Classes_Names() {
        // given
        final String expectedClass1Name = A.class.getName();
        final String expectedClass2Name = B.class.getName();

        // when
        final MultiClassAssertion result = (MultiClassAssertion) Assertions.assertPojoMethodsForAll(expectedClass1Name,
                                                                                                    expectedClass2Name);
        List<ClassAndFieldPredicatePair> pairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(pairs).usingRecursiveFieldByFieldElementComparator()
                         .containsExactlyInAnyOrder(new ClassAndFieldPredicatePair(A.class),
                                                    new ClassAndFieldPredicatePair(B.class));
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Class_And_Field_Predicate_Pairs() {
        // given
        final Class<A> expectedClass1 = A.class;
        final Class<B> expectedClass2 = B.class;
        final ClassAndFieldPredicatePair pair1 = new ClassAndFieldPredicatePair(expectedClass1);
        final ClassAndFieldPredicatePair pair2 = new ClassAndFieldPredicatePair(expectedClass2);

        // when
        final MultiClassAssertion result = (MultiClassAssertion) Assertions.assertPojoMethodsForAll(pair1,
                                                                                                    pair2,
                                                                                                    pair2);
        List<ClassAndFieldPredicatePair> pairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(pairs).usingRecursiveFieldByFieldElementComparator()
                         .containsExactlyInAnyOrder(new ClassAndFieldPredicatePair(A.class),
                                                    new ClassAndFieldPredicatePair(B.class),
                                                    new ClassAndFieldPredicatePair(B.class));
    }

    @Data
    private class A {
        private int a;
    }

    @Data
    private class B {
        private int b;
    }

    private class ClassAndFieldPredicatePairCondition extends Condition<ClassAndFieldPredicatePair> {
        final ClassAndFieldPredicatePairArgumentMatcher classAndFieldPredicatePairArgumentMatcher;

        ClassAndFieldPredicatePairCondition(final Class<?> expectedClass, final String stringToMatchPredicate) {
            this.classAndFieldPredicatePairArgumentMatcher = new ClassAndFieldPredicatePairArgumentMatcher(expectedClass,
                                                                                                           stringToMatchPredicate);
        }

        @Override
        public boolean matches(final ClassAndFieldPredicatePair value) {
            return classAndFieldPredicatePairArgumentMatcher.matches(value);
        }
    }

    private class ClassNameAndFieldPredicatePairCondition extends Condition<ClassAndFieldPredicatePair> {
        final ClassNameAndFieldPredicatePairArgumentMatcher classAndFieldPredicatePairArgumentMatcher;

        ClassNameAndFieldPredicatePairCondition(final String expectedClass, final String stringToMatchPredicate) {
            this.classAndFieldPredicatePairArgumentMatcher = new ClassNameAndFieldPredicatePairArgumentMatcher(
                    expectedClass,
                    stringToMatchPredicate);
        }

        @Override
        public boolean matches(final ClassAndFieldPredicatePair value) {
            return classAndFieldPredicatePairArgumentMatcher.matches(value);
        }
    }
}
