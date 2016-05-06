package org.pojo.tester;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldUtils;
import test.predicate.TestPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.util.Lists.newArrayList;
import static org.pojo.tester.PredicateAssertions.assertThat;


@RunWith(JUnitParamsRunner.class)
public class FieldPredicateTest {

    @Test
    public void shouldIncludeAllFields() {
        // given
        List<String> allFieldNames = FieldUtils.getAllFieldNames(TestPredicate.class);

        // when
        final Predicate<String> predicate = FieldPredicate.includeAllFields(TestPredicate.class);

        // then
        assertThat(predicate).accepts(allFieldNames);
    }

    @Test
    @Parameters(method = "listOfIncludedFields")
    public void shouldIncludeListOfFields(final List<String> includedFields) {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.include(includedFields);

        // then
        assertThat(predicate).accepts(includedFields);
    }

    @Test
    @Parameters(method = "arrayOfIncludedFields")
    public void shouldIncludeArrayOfFields(final String... includedFields) {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.include(includedFields);

        // then
        assertThat(predicate).accepts(includedFields);
    }

    @Test
    @Parameters(method = "listOfExcludedFields")
    public void shouldExcludeListOfFields(final List<String> excludedFields) {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.exclude(excludedFields);

        // then
        assertThat(predicate).doesNotAccept(excludedFields);
    }

    @Test
    @Parameters(method = "arrayOfExcludedFields")
    public void shouldExcludeArrayOfFields(final String... excludedFields) {
        // given

        // when
        final Predicate<String> predicate = FieldPredicate.exclude(excludedFields);

        // then
        assertThat(predicate).doesNotAccept(excludedFields);
    }


    private Object[][] listOfExcludedFields() {
        return new Object[][]{{newArrayList("a")},
                              {newArrayList("a", "b")},
                              {newArrayList("a", "b", "c")},};
    }

    private Object[][] arrayOfExcludedFields() {
        return new Object[][]{{new String[]{"a"}},
                              {new String[]{"a", "b"}},
                              {new String[]{"a", "b", "c"}},};
    }


    private Object[][] listOfIncludedFields() {
        return new Object[][]{{newArrayList("a")},
                              {newArrayList("a", "b")},
                              {newArrayList("a", "b", "c")},};
    }

    private Object[][] arrayOfIncludedFields() {
        return new Object[][]{{new String[]{"a"}},
                              {new String[]{"a", "b"}},
                              {new String[]{"a", "b", "c"}},};
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
