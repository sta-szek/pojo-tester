package org.pojo.tester;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.utils.FieldUtils;
import test.predicate.TestPredicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class FieldPredicateTest {

    @Test
    public void Should_Return_Predicate_That_Accept_All_Field_Names() {
        // given
        final List<String> allFieldNames = FieldUtils.getAllFieldNames(TestPredicate.class);

        // when
        final Predicate<String> result = FieldPredicate.includeAllFields(TestPredicate.class);

        // then
        assertThat(result).acceptsAll(allFieldNames);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Accept_List_Of_Field_Names() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_List_Of_Field_Names(value)));
    }

    public Executable Should_Return_Predicate_That_Accept_List_Of_Field_Names(final List<String> includedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.include(includedFields);

            // then
            assertThat(result).acceptsAll(includedFields);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Accept_Array_Of_Field_Names() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_Array_Of_Field_Names(value)));
    }

    public Executable Should_Return_Predicate_That_Accept_Array_Of_Field_Names(final String... includedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.include(includedFields);

            // then
            assertThat(result).accepts(includedFields);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(value)));
    }

    public Executable Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(final List<String> excludedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(result).rejectsAll(excludedFields);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(value)));
    }

    public Executable Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(final String... excludedFields) {
        return () -> {
            // when
            final Predicate<String> result = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(result).rejects(excludedFields);
        };
    }

}
