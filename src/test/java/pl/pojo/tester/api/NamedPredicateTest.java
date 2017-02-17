package pl.pojo.tester.api;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class NamedPredicateTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_To_String() throws NoSuchFieldException {
        final NamedPredicate<String> a = new NamedPredicate<>("a", Predicate.isEqual(null));
        final NamedPredicate<String> b = new NamedPredicate<>("b", Predicate.isEqual(null));
        final NamedPredicate<String> c = new NamedPredicate<>("c", Predicate.isEqual(null));
        final NamedPredicate<String> d = new NamedPredicate<>("d", Predicate.isEqual(null));
        final NamedPredicate<String> empty = new NamedPredicate<>(Predicate.isEqual(null));
        return Stream.of(
                new NamedPredicateTestCase(a, "a"),
                new NamedPredicateTestCase(a.negate(), "!(a)"),
                new NamedPredicateTestCase(a.negate().negate(), "a"),
                new NamedPredicateTestCase(a.negate().and(b), "!(a),b"),
                new NamedPredicateTestCase(a.negate().and(b.negate()), "!(a),!(b)"),
                new NamedPredicateTestCase(a.negate().and(b.negate()).negate(), "!(!(a),!(b))"),
                new NamedPredicateTestCase(a.negate().and(b.negate()).negate().negate(), "!(!(!(a),!(b)))"),
                new NamedPredicateTestCase(a.negate().and(b.negate()).negate().negate().or(c), "!(!(!(a),!(b))),c"),
                new NamedPredicateTestCase(a.negate().and(b.negate()).negate().negate().or(c).and(d),
                                           "!(!(!(a),!(b))),c,d"),
                new NamedPredicateTestCase(a.negate().and(b.negate()).negate().negate().or(c).and(d.negate()),
                                           "!(!(!(a),!(b))),c,!(d)"),
                new NamedPredicateTestCase(a.or(b.or(c.or(d))), "a,b,c,d"),
                new NamedPredicateTestCase(a.or(b).or(c).or(d), "a,b,c,d"),
                new NamedPredicateTestCase(a.and(b.and(c.and(d))), "a,b,c,d"),
                new NamedPredicateTestCase(a.and(b).and(c).and(d), "a,b,c,d"),
                new NamedPredicateTestCase(empty, ""),
                new NamedPredicateTestCase(empty.and(a), "a"),
                new NamedPredicateTestCase(empty.or(a), "a")
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value.predicate.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final NamedPredicateTestCase testCase) {
        return () -> {
            // when
            final String result = testCase.predicate.toString();

            // then
            assertThat(result).isEqualTo(testCase.expectedResult);
        };
    }

    @Test
    public void Should_Return_True_If_Given_Predicate_Returns_True() {
        // given
        final NamedPredicate<Object> predicate = new NamedPredicate<>(Predicate.isEqual(null));

        // when
        final boolean result = predicate.test(null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_False_If_Given_Predicate_Returns_False() {
        // given
        final NamedPredicate<Object> predicate = new NamedPredicate<>(Predicate.isEqual(null));

        // when
        final boolean result = predicate.test(new Object());

        // then
        assertThat(result).isFalse();
    }

    @AllArgsConstructor
    private class NamedPredicateTestCase {
        private NamedPredicate<String> predicate;
        private String expectedResult;
    }
}