package pl.pojo.tester.api;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import pl.pojo.tester.internal.utils.FieldUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class FieldPredicateTest {

    @Test
    void Should_Return_Predicate_That_Accept_All_Field_Names() {
        // given
        final List<String> allFieldNames = FieldUtils.getAllFieldNames(TestPredicate.class);

        // when
        final Predicate<String> result = FieldPredicate.includeAllFields(TestPredicate.class);

        // then
        assertThat(result).acceptsAll(allFieldNames);
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_Predicate_That_Accept_List_Of_Field_Names() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_List_Of_Field_Names(value)));
    }

    private Executable Should_Return_Predicate_That_Accept_List_Of_Field_Names(final List<String> includedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.include(includedFields);

            // then
            assertThat(result).acceptsAll(includedFields);
        };
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_Predicate_That_Accept_Array_Of_Field_Names() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_Array_Of_Field_Names(value)));
    }

    private Executable Should_Return_Predicate_That_Accept_Array_Of_Field_Names(final String... includedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.include(includedFields);

            // then
            assertThat(result).accepts(includedFields);
        };
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(value)));
    }

    private Executable Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(final List<String> excludedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(result).rejectsAll(excludedFields);
        };
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(value)));
    }

    private Executable Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(final String... excludedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(result).rejects(excludedFields);
        };
    }

    private class TestPredicate {

        private int a;
        private int b;
        private int c;
    }
}
