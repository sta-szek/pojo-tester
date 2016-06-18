package org.pojo.tester.field.collections.collection;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.fields.collections.collection.Collections;

import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AbstractCollectionFieldValueChangerTest {

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final AbstractCollectionFieldValueChanger changer,
                                                                      final Field field,
                                                                      final boolean expectedResult) {
        // given

        // when
        final boolean result = changer.canChange(field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AbstractCollectionFieldValueChanger changer,
                                                                                final Collection value1,
                                                                                final Collection value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = changer.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        final Collection collectionABC = new ArrayList<>();
        collectionABC.add("A");
        collectionABC.add("B");
        collectionABC.add("C");

        final Collection collectionAB = new ArrayList<>();
        collectionAB.add("A");
        collectionAB.add("B");

        return new Object[][]{
                {new ArrayListValueChanger(), null, null, false},
                {new ArrayListValueChanger(), new ArrayList<>(), new ArrayList<>(), false},
                {new ArrayListValueChanger(), new ArrayList<>(collectionABC), new ArrayList<>(collectionABC), false},
                {new ArrayListValueChanger(), null, new ArrayList<>(), true},
                {new ArrayListValueChanger(), new ArrayList<>(collectionAB), new ArrayList<>(collectionABC), true},

                {new DequeValueChanger(), null, null, false},
                {new DequeValueChanger(), new LinkedList<>(), new LinkedList<>(), false},
                {new DequeValueChanger(), new LinkedList<>(collectionABC), new LinkedList<>(collectionABC), false},
                {new DequeValueChanger(), null, new LinkedList<>(), true},
                {new DequeValueChanger(), new LinkedList<>(collectionAB), new LinkedList<>(collectionABC), true},

                {new HashSetValueChanger(), null, null, false},
                {new HashSetValueChanger(), new HashSet<>(), new HashSet<>(), false},
                {new HashSetValueChanger(), new HashSet<>(collectionABC), new HashSet<>(collectionABC), false},
                {new HashSetValueChanger(), null, new HashSet<>(), true},
                {new HashSetValueChanger(), new HashSet<>(collectionAB), new HashSet<>(collectionABC), true},

                {new LinkedHashSetValueChanger(), null, null, false},
                {new LinkedHashSetValueChanger(), new LinkedHashSet<>(), new LinkedHashSet<>(), false},
                {new LinkedHashSetValueChanger(), new LinkedHashSet<>(collectionABC), new LinkedHashSet<>(collectionABC), false},
                {new LinkedHashSetValueChanger(), null, new LinkedHashSet<>(), true},
                {new LinkedHashSetValueChanger(), new LinkedHashSet<>(collectionAB), new LinkedHashSet<>(collectionABC), true},

                {new LinkedListValueChanger(), null, null, false},
                {new LinkedListValueChanger(), new LinkedList<>(), new LinkedList<>(), false},
                {new LinkedListValueChanger(), new LinkedList<>(collectionABC), new LinkedList<>(collectionABC), false},
                {new LinkedListValueChanger(), null, new LinkedList<>(), true},
                {new LinkedListValueChanger(), new LinkedList<>(collectionAB), new LinkedList<>(collectionABC), true},

                {new ListValueChanger(), null, null, false},
                {new ListValueChanger(), new ArrayList<>(), new ArrayList<>(), false},
                {new ListValueChanger(), new ArrayList<>(collectionABC), new ArrayList<>(collectionABC), false},
                {new ListValueChanger(), null, new ArrayList<>(), true},
                {new ListValueChanger(), new ArrayList<>(collectionAB), new ArrayList<>(collectionABC), true},

                {new QueueValueChanger(), null, null, false},
                {new QueueValueChanger(), new LinkedList<>(), new LinkedList<>(), false},
                {new QueueValueChanger(), new LinkedList<>(collectionABC), new LinkedList<>(collectionABC), false},
                {new QueueValueChanger(), null, new LinkedList<>(), true},
                {new QueueValueChanger(), new LinkedList<>(collectionAB), new LinkedList<>(collectionABC), true},

                {new SetValueChanger(), null, null, false},
                {new SetValueChanger(), new HashSet<>(), new HashSet<>(), false},
                {new SetValueChanger(), new HashSet<>(collectionABC), new HashSet<>(collectionABC), false},
                {new SetValueChanger(), null, new HashSet<>(), true},
                {new SetValueChanger(), new HashSet<>(collectionAB), new HashSet<>(collectionABC), true},

                {new SortedSetValueChanger(), null, null, false},
                {new SortedSetValueChanger(), new TreeSet<>(), new TreeSet<>(), false},
                {new SortedSetValueChanger(), new TreeSet<>(collectionABC), new TreeSet<>(collectionABC), false},
                {new SortedSetValueChanger(), null, new TreeSet<>(), true},
                {new SortedSetValueChanger(), new TreeSet<>(collectionAB), new TreeSet<>(collectionABC), true},
                };
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field arrayList = Collections.class.getDeclaredField("arrayList");
        final Field deque = Collections.class.getDeclaredField("deque");
        final Field hashSet = Collections.class.getDeclaredField("hashSet");
        final Field linkedHashSet = Collections.class.getDeclaredField("linkedHashSet");
        final Field linkedList = Collections.class.getDeclaredField("linkedList");
        final Field list = Collections.class.getDeclaredField("list");
        final Field queue = Collections.class.getDeclaredField("queue");
        final Field set = Collections.class.getDeclaredField("set");
        final Field sortedSet = Collections.class.getDeclaredField("sortedSet");
        final Field stack = Collections.class.getDeclaredField("stack");
        final Field treeSet = Collections.class.getDeclaredField("treeSet");
        final Field vector = Collections.class.getDeclaredField("vector");

        return new Object[][]{
                {new ArrayListValueChanger(), arrayList, true},
                {new DequeValueChanger(), deque, true},
                {new HashSetValueChanger(), hashSet, true},
                {new LinkedHashSetValueChanger(), linkedHashSet, true},
                {new LinkedListValueChanger(), linkedList, true},
                {new ListValueChanger(), list, true},
                {new QueueValueChanger(), queue, true},
                {new SetValueChanger(), set, true},
                {new SortedSetValueChanger(), sortedSet, true},
                {new StackValueChanger(), stack, true},
                {new TreeSetValueChanger(), treeSet, true},
                {new VectorValueChanger(), vector, true},
                };
    }
}
