package pl.pojo.tester.internal.instantiator;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


public class CollectionInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Collection_Object() {
        return Stream.of(Stream.class,
                         Collection.class,
                         List.class,
                         Stack.class,
                         Vector.class,
                         ArrayList.class,
                         LinkedList.class,
                         Queue.class,
                         Deque.class,
                         Set.class,
                         HashSet.class,
                         LinkedHashSet.class,
                         SortedSet.class,
                         NavigableSet.class,
                         TreeSet.class,
                         Iterator.class,
                         Iterable.class,
                         Map.class,
                         HashMap.class,
                         LinkedHashMap.class,
                         Hashtable.class,
                         SortedMap.class,
                         NavigableMap.class,
                         TreeMap.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Collection_Object(value)));
    }

    public Executable Should_Return_Expected_Collection_Object(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final CollectionInstantiator instantiator = new CollectionInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

    @Test
    public void Should_Throws_Exception_When_Prepared_Objects_Do_Not_Contain_Expected_Class() {
        // given
        final CollectionInstantiator instantiator = new CollectionInstantiator(String.class);

        // when
        final Throwable result = catchThrowable(instantiator::instantiate);

        // then
        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }

}
