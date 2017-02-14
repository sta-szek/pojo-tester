package pl.pojo.tester.api;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
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
                new NamedPredicateTestCase(a.and(b).and(c).and(d), "a,b,c,d")
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value.predicate.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    private Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final NamedPredicateTestCase testCase) {
        return () -> {
            // when
            final String result = testCase.predicate.toString();

            // then
            assertThat(result).isEqualTo(testCase.expectedResult);
        };
    }

    @AllArgsConstructor
    private class NamedPredicateTestCase {
        private NamedPredicate<String> predicate;
        private String expectedResult;
    }
}