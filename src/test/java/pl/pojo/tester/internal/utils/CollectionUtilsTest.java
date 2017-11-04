package pl.pojo.tester.internal.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class CollectionUtilsTest {

    @Test
    void Should_Return_True_If_Collection_Is_Not_Empty_And_Not_Null() {
        // given
        final List<Object> collection = new ArrayList<>();
        collection.add(new Object());

        // when
        final boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_Return_False_If_Collection_Is_Empty() {
        // given
        final List<Object> collection = new ArrayList<>();

        // when
        final boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_Return_False_If_Collection_Is_Null() {
        // given
        final List<Object> collection = null;

        // when
        final boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_Return_False_If_Collection_Is_Not_Empty_And_Not_Null() {
        // given
        final List<Object> collection = new ArrayList<>();
        collection.add(new Object());

        // when
        final boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_Return_True_If_Collection_Is_Empty() {
        // given
        final List<Object> collection = new ArrayList<>();

        // when
        final boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_Return_True_If_Collection_Is_Null() {
        // given
        final List<Object> collection = null;

        // when
        final boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_Return_List_With_Given_Elements() {
        // given
        final String element1 = "a";
        final String element2 = "b";

        // when
        final List<String> result = CollectionUtils.asList(element1, element2);

        // then
        assertThat(result).containsExactly(element1, element2);
    }

    @Test
    void Should_Return_Set_With_Given_Elements() {
        // given
        final String element1 = "a";
        final String element2 = "b";

        // when
        final Set<String> result = CollectionUtils.asSet(element1, element2);

        // then
        assertThat(result).containsExactly(element1, element2);
    }
}