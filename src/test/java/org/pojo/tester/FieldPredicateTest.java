package org.pojo.tester;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldUtils;
import test.predicate.TestPredicate;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.pojo.tester.PredicateAssertions.assertThat;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class FieldPredicateTest {

    @Test
    public void Should_Return_Predicate_That_Accept_All_Field_Names() {
        // given
        final List<String> allFieldNames = FieldUtils.getAllFieldNames(TestPredicate.class);

        // when
        final Predicate<String> predicate = FieldPredicate.includeAllFields(TestPredicate.class);

        // then
        assertThat(predicate).accepts(allFieldNames);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Accept_List_Of_Field_Names() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_List_Of_Field_Names(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Accept_Array_Of_Field_Names() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Accept_Array_Of_Field_Names(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields() {
        return Stream.of(newArrayList("a"),
                         newArrayList("a", "b"),
                         newArrayList("a", "b", "c"))
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields() {
        return Stream.of(new String[]{"a"},
                         new String[]{"a", "b"},
                         new String[]{"a", "b", "c"})


                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(value)));
    }

    private Executable Should_Return_Predicate_That_Does_Not_Accept_Array_Of_Fields(final String... excludedFields) {
        return () -> {
            // when
            final Predicate<String> predicate = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(predicate).doesNotAccept(excludedFields);
        };
    }

    private Executable Should_Return_Predicate_That_Does_Not_Accept_List_Of_Fields(final List<String> excludedFields) {
        return () -> {
            // when
            final Predicate<String> predicate = FieldPredicate.exclude(excludedFields);

            // then
            assertThat(predicate).doesNotAccept(excludedFields);
        };
    }

    private Executable Should_Return_Predicate_That_Accept_Array_Of_Field_Names(final String... includedFields) {
        return () -> {
            // when
            final Predicate<String> predicate = FieldPredicate.include(includedFields);

            // then
            assertThat(predicate).accepts(includedFields);
        };
    }

    private Executable Should_Return_Predicate_That_Accept_List_Of_Field_Names(final List<String> includedFields) {
        return () -> {
            // when
            final Predicate<String> predicate = FieldPredicate.include(includedFields);

            // then
            assertThat(predicate).accepts(includedFields);
        };
    }

}

class PredicateAssertions {

    private final Predicate<String> predicate;

    private PredicateAssertions(final Predicate<String> predicate) {
        this.predicate = predicate;
    }

    static PredicateAssertions assertThat(final Predicate<String> predicate) {
        return new PredicateAssertions(predicate);
    }

    void doesNotAccept(final String[] values) {
        doesNotAccept(Arrays.asList(values));
    }

    void doesNotAccept(final List<String> values) {
        values.forEach(value -> {
            final boolean result = predicate.test(value);
            org.assertj.core.api.Assertions.assertThat(result)
                                           .isFalse();
        });
    }

    void accepts(final String[] values) {
        accepts(Arrays.asList(values));
    }

    void accepts(final List<String> values) {
        values.forEach(value -> {
            final boolean result = predicate.test(value);
            org.assertj.core.api.Assertions.assertThat(result)
                                           .isTrue();
        });
    }
}
