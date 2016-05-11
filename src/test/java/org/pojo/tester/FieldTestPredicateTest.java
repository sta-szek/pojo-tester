package org.pojo.tester;

import org.assertj.core.util.Lists;
import org.junit.Test;
import test.predicate.TestPredicate;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


public class FieldTestPredicateTest {

    @Test
    public void Should_Return_Predicate_That_Accept_All_Fields() {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.includeAllFields(TestPredicate.class);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @Test
    public void Should_Return_Predicate_That_Accept_List_Of_Fields() {
        // given
        final List<String> fields = Lists.newArrayList("a", "b");

        // when
        final Predicate<String> predicate = FieldPredicate.include(fields);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isFalse();
    }

    @Test
    public void Should_Return_Predicate_That_Accept_Array_Of_Fields() {
        // given
        final String[] fields = {"a", "b"};

        // when
        final Predicate<String> predicate = FieldPredicate.include(fields);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isFalse();
    }

    @Test
    public void Should_Return_Predicate_That_Refuse_List_Of_Fields() {
        // given
        final List<String> fields = Lists.newArrayList("a", "b");

        // when
        final Predicate<String> predicate = FieldPredicate.exclude(fields);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();
    }

    @Test
    public void Should_Return_Predicate_That_Refuse_Array_Of_Fields() {
        // given
        final String[] fields = {"a", "b"};

        // when
        final Predicate<String> predicate = FieldPredicate.exclude(fields);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
        assertThat(result3).isTrue();
    }
}
