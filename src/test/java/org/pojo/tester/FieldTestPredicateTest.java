package org.pojo.tester;

import org.assertj.core.util.Lists;
import org.junit.Test;
import test.predicate.TestPredicate;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


public class FieldTestPredicateTest {

    @Test
    public void shouldReturnPredicate_ThatAcceptAllFields() {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.acceptAllFields(TestPredicate.class);
        final boolean result1 = predicate.test("a");
        final boolean result2 = predicate.test("b");
        final boolean result3 = predicate.test("c");

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @Test
    public void shouldReturnPredicate_ThatAcceptListOfFields() {
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
    public void shouldReturnPredicate_ThatAcceptArrayOfFields() {
        // given
        String[] fields = {"a", "b"};

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
    public void shouldReturnPredicate_ThatRefuseListOfFields() {
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
    public void shouldReturnPredicate_ThatRefuseArrayOfFields() {
        // given
        String[] fields = {"a", "b"};

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