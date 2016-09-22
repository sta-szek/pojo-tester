package pl.pojo.tester.api.assertion;

import java.util.List;
import java.util.function.Predicate;
import lombok.Data;
import matchers.ClassAndFieldPredicatePairArgumentMatcher;
import matchers.ClassNameAndFieldPredicatePairArgumentMatcher;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)
public class AssertionsTest {

    @Test
    public void Should_Create_Expected_Single_Class_Assertion_Using_Class() {
        // given
        final Class<A> expectedClass = A.class;
        final String fieldName = "a";
        final ClassAndFieldPredicatePairCondition conditionToMatch = new ClassAndFieldPredicatePairCondition(expectedClass, fieldName);

        // when
        final SingleClassAssetion result = (SingleClassAssetion) Assertions.assertPojoMethodsFor(expectedClass);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result, "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

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
        final ClassNameAndFieldPredicatePairCondition conditionToMatch = new ClassNameAndFieldPredicatePairCondition(expectedClassName, fieldName);

        // when
        final SingleClassAssetion result = (SingleClassAssetion) Assertions.assertPojoMethodsFor(expectedClassName);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result, "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

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
        final ClassNameAndFieldPredicatePairCondition conditionToMatch = new ClassNameAndFieldPredicatePairCondition(expectedClassName, fieldName);

        // when
        final SingleClassAssetion result = (SingleClassAssetion) Assertions.assertPojoMethodsFor(expectedClassName, predicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result, "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

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
        final ClassAndFieldPredicatePairCondition conditionToMatch = new ClassAndFieldPredicatePairCondition(expectedClass, fieldName);

        // when
        final SingleClassAssetion result = (SingleClassAssetion) Assertions.assertPojoMethodsFor(expectedClass, predicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result, "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

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
        final SingleClassAssetion result = (SingleClassAssetion) Assertions.assertPojoMethodsFor(expectedClassAndFieldPredicate,
                                                                                                 expectedClassAndFieldPredicate);
        final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair = getInternalState(result, "baseClassAndFieldPredicatePair");
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(baseClassAndFieldPredicatePair).isEqualTo(expectedClassAndFieldPredicate);
        assertThat(classAndFieldPredicatePairs).containsExactly(expectedClassAndFieldPredicate);
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Classes() {
        // given
        final Class<A> expectedClass1 = A.class;
        final Class<B> expectedClass2 = B.class;


        // when
        final MultiClassAssetion result = (MultiClassAssetion) Assertions.assertPojoMethodsForAll(expectedClass1, expectedClass2);
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(classAndFieldPredicatePairs).hasSize(2);
        assertThat(classAndFieldPredicatePairs.get(0)).is(new ClassAndFieldPredicatePairCondition(expectedClass1, "a"));
        assertThat(classAndFieldPredicatePairs.get(1)).is(new ClassAndFieldPredicatePairCondition(expectedClass2, "b"));
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Classes_Names() {
        // given
        final String expectedClass1Name = A.class.getName();
        final String expectedClass2Name = B.class.getName();


        // when
        final MultiClassAssetion result = (MultiClassAssetion) Assertions.assertPojoMethodsForAll(expectedClass1Name, expectedClass2Name);
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(classAndFieldPredicatePairs).hasSize(2);
        assertThat(classAndFieldPredicatePairs.get(0)).is(new ClassNameAndFieldPredicatePairCondition(expectedClass1Name, "a"));
        assertThat(classAndFieldPredicatePairs.get(1)).is(new ClassNameAndFieldPredicatePairCondition(expectedClass2Name, "b"));
    }

    @Test
    public void Should_Create_Expected_Multi_Class_Assertion_Using_Class_And_Field_Predicate_Pairs() {
        // given
        final Class<A> expectedClass1 = A.class;
        final Class<B> expectedClass2 = B.class;
        final ClassAndFieldPredicatePair pair1 = new ClassAndFieldPredicatePair(expectedClass1);
        final ClassAndFieldPredicatePair pair2 = new ClassAndFieldPredicatePair(expectedClass2);


        // when
        final MultiClassAssetion result = (MultiClassAssetion) Assertions.assertPojoMethodsForAll(pair1, pair2, pair2);
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = getInternalState(result, "classAndFieldPredicatePairs");

        // then
        assertThat(classAndFieldPredicatePairs).hasSize(3);
        assertThat(classAndFieldPredicatePairs.get(0)).is(new ClassAndFieldPredicatePairCondition(expectedClass1, "a"));
        assertThat(classAndFieldPredicatePairs.get(1)).is(new ClassAndFieldPredicatePairCondition(expectedClass2, "b"));
        assertThat(classAndFieldPredicatePairs.get(2)).is(new ClassAndFieldPredicatePairCondition(expectedClass2, "b"));
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
            this.classAndFieldPredicatePairArgumentMatcher = new ClassAndFieldPredicatePairArgumentMatcher(expectedClass, stringToMatchPredicate);
        }

        @Override
        public boolean matches(final ClassAndFieldPredicatePair value) {
            return classAndFieldPredicatePairArgumentMatcher.matches(value);
        }
    }

    private class ClassNameAndFieldPredicatePairCondition extends Condition<ClassAndFieldPredicatePair> {
        final ClassNameAndFieldPredicatePairArgumentMatcher classAndFieldPredicatePairArgumentMatcher;

        ClassNameAndFieldPredicatePairCondition(final String expectedClass, final String stringToMatchPredicate) {
            this.classAndFieldPredicatePairArgumentMatcher = new ClassNameAndFieldPredicatePairArgumentMatcher(expectedClass, stringToMatchPredicate);
        }

        @Override
        public boolean matches(final ClassAndFieldPredicatePair value) {
            return classAndFieldPredicatePairArgumentMatcher.matches(value);
        }
    }
}
