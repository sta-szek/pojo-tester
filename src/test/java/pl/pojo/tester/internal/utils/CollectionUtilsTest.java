package pl.pojo.tester.internal.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class CollectionUtilsTest {

    @Test
    public void Should_Return_True_If_Collection_Is_Not_Empty_And_Not_Null() {
        // given
        List<Object> collection = new ArrayList<>();
        collection.add(new Object());

        // when
        boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_False_If_Collection_Is_Empty() {
        // given
        List<Object> collection = new ArrayList<>();

        // when
        boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Return_False_If_Collection_Is_Null() {
        // given
        List<Object> collection = null;

        // when
        boolean result = CollectionUtils.isNotEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Return_False_If_Collection_Is_Not_Empty_And_Not_Null() {
        // given
        List<Object> collection = new ArrayList<>();
        collection.add(new Object());

        // when
        boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Return_True_If_Collection_Is_Empty() {
        // given
        List<Object> collection = new ArrayList<>();

        // when
        boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_True_If_Collection_Is_Null() {
        // given
        List<Object> collection = null;

        // when
        boolean result = CollectionUtils.isEmpty(collection);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_List_With_Given_Elements() {
        // given
        String element1 = "a";
        String element2 = "b";

        // when
        List<String> result = CollectionUtils.asList(element1, element2);

        // then
        assertThat(result).containsExactly(element1, element2);
    }

    @Test
    public void Should_Return_Set_With_Given_Elements() {
        // given
        String element1 = "a";
        String element2 = "b";

        // when
        Set<String> result = CollectionUtils.asSet(element1, element2);

        // then
        assertThat(result).containsExactly(element1, element2);
    }
}