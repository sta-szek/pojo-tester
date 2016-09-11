package pl.pojo.tester.internal.field.collections.collection;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.fields.collections.collection.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class AbstractCollectionFieldValueChangerTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(new ArrayListValueChanger(), Collections.class.getDeclaredField("arrayList"), true),
                         new CanChangeCase(new DequeValueChanger(), Collections.class.getDeclaredField("deque"), true),
                         new CanChangeCase(new HashSetValueChanger(), Collections.class.getDeclaredField("hashSet"), true),
                         new CanChangeCase(new LinkedHashSetValueChanger(), Collections.class.getDeclaredField("linkedHashSet"), true),
                         new CanChangeCase(new LinkedListValueChanger(), Collections.class.getDeclaredField("linkedList"), true),
                         new CanChangeCase(new ListValueChanger(), Collections.class.getDeclaredField("list"), true),
                         new CanChangeCase(new QueueValueChanger(), Collections.class.getDeclaredField("queue"), true),
                         new CanChangeCase(new SetValueChanger(), Collections.class.getDeclaredField("set"), true),
                         new CanChangeCase(new SortedSetValueChanger(), Collections.class.getDeclaredField("sortedSet"), true),
                         new CanChangeCase(new StackValueChanger(), Collections.class.getDeclaredField("stack"), true),
                         new CanChangeCase(new TreeSetValueChanger(), Collections.class.getDeclaredField("treeSet"), true),
                         new CanChangeCase(new VectorValueChanger(), Collections.class.getDeclaredField("vector"), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.field.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final CanChangeCase testCase) {
        return () -> {
            // when
            final boolean result = testCase.valueChanger.canChange(testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        final Collection<String> collectionABC = new ArrayList<>();
        collectionABC.add("A");
        collectionABC.add("B");
        collectionABC.add("C");

        final Collection<String> collectionAB = new ArrayList<>();
        collectionAB.add("A");
        collectionAB.add("B");

        final ArrayList arrayListABC = new ArrayList<>(collectionABC);
        final LinkedList linkedListABC = new LinkedList<>(collectionABC);
        final LinkedHashSet linkedHashSetABC = new LinkedHashSet<>(collectionABC);
        final TreeSet treeSetABC = new TreeSet<>(collectionABC);
        return Stream.of(new AreDifferentCase(new ArrayListValueChanger(), null, null, false),
                         new AreDifferentCase(new ArrayListValueChanger(), new ArrayList<>(), new ArrayList<>(), false),
                         new AreDifferentCase(new ArrayListValueChanger(), arrayListABC, arrayListABC, false),
                         new AreDifferentCase(new ArrayListValueChanger(), null, new ArrayList<>(), true),
                         new AreDifferentCase(new ArrayListValueChanger(), new ArrayList<>(collectionAB), arrayListABC, true),
                         new AreDifferentCase(new DequeValueChanger(), null, null, false),
                         new AreDifferentCase(new DequeValueChanger(), new LinkedList<>(), new LinkedList<>(), false),
                         new AreDifferentCase(new DequeValueChanger(), linkedListABC, linkedListABC, false),
                         new AreDifferentCase(new DequeValueChanger(), new LinkedList<>(), null, true),
                         new AreDifferentCase(new DequeValueChanger(), new LinkedList<>(collectionAB), linkedListABC, true),
                         new AreDifferentCase(new HashSetValueChanger(), null, null, false),
                         new AreDifferentCase(new HashSetValueChanger(), new HashSet<>(), new HashSet<>(), false),
                         new AreDifferentCase(new HashSetValueChanger(), new HashSet<>(collectionABC), new HashSet<>(collectionABC), false),
                         new AreDifferentCase(new HashSetValueChanger(), null, new HashSet<>(), true),
                         new AreDifferentCase(new HashSetValueChanger(), new HashSet<>(collectionAB), new HashSet<>(collectionABC), true),
                         new AreDifferentCase(new LinkedHashSetValueChanger(), null, null, false),
                         new AreDifferentCase(new LinkedHashSetValueChanger(), new LinkedHashSet<>(), new LinkedHashSet<>(), false),
                         new AreDifferentCase(new LinkedHashSetValueChanger(), linkedHashSetABC, linkedHashSetABC, false),
                         new AreDifferentCase(new LinkedHashSetValueChanger(), null, new LinkedHashSet<>(), true),
                         new AreDifferentCase(new LinkedHashSetValueChanger(), new LinkedHashSet<>(collectionAB), linkedHashSetABC, true),
                         new AreDifferentCase(new LinkedListValueChanger(), null, null, false),
                         new AreDifferentCase(new LinkedListValueChanger(), new LinkedList<>(), new LinkedList<>(), false),
                         new AreDifferentCase(new LinkedListValueChanger(), linkedListABC, linkedListABC, false),
                         new AreDifferentCase(new LinkedListValueChanger(), null, new LinkedList<>(), true),
                         new AreDifferentCase(new LinkedListValueChanger(), new LinkedList<>(collectionAB), linkedListABC, true),
                         new AreDifferentCase(new ListValueChanger(), null, null, false),
                         new AreDifferentCase(new ListValueChanger(), new ArrayList<>(), new ArrayList<>(), false),
                         new AreDifferentCase(new ListValueChanger(), arrayListABC, arrayListABC, false),
                         new AreDifferentCase(new ListValueChanger(), null, new ArrayList<>(), true),
                         new AreDifferentCase(new ListValueChanger(), new ArrayList<>(collectionAB), arrayListABC, true),
                         new AreDifferentCase(new QueueValueChanger(), null, null, false),
                         new AreDifferentCase(new QueueValueChanger(), new LinkedList<>(), new LinkedList<>(), false),
                         new AreDifferentCase(new QueueValueChanger(), linkedListABC, linkedListABC, false),
                         new AreDifferentCase(new QueueValueChanger(), null, new LinkedList<>(), true),
                         new AreDifferentCase(new QueueValueChanger(), new LinkedList<>(collectionAB), linkedListABC, true),
                         new AreDifferentCase(new SetValueChanger(), null, null, false),
                         new AreDifferentCase(new SetValueChanger(), new HashSet<>(), new HashSet<>(), false),
                         new AreDifferentCase(new SetValueChanger(), new HashSet<>(collectionABC), new HashSet<>(collectionABC), false),
                         new AreDifferentCase(new SetValueChanger(), null, new HashSet<>(), true),
                         new AreDifferentCase(new SetValueChanger(), new HashSet<>(collectionAB), new HashSet<>(collectionABC), true),
                         new AreDifferentCase(new SortedSetValueChanger(), null, null, false),
                         new AreDifferentCase(new SortedSetValueChanger(), new TreeSet<>(), new TreeSet<>(), false),
                         new AreDifferentCase(new SortedSetValueChanger(), treeSetABC, treeSetABC, false),
                         new AreDifferentCase(new SortedSetValueChanger(), null, new TreeSet<>(), true),
                         new AreDifferentCase(new SortedSetValueChanger(), new TreeSet<>(collectionAB), treeSetABC, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // when
            final boolean result = testCase.valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @AllArgsConstructor
    private class CanChangeCase {
        private AbstractCollectionFieldValueChanger valueChanger;
        private Field field;
        private boolean result;
    }

    @AllArgsConstructor
    private class AreDifferentCase {
        private AbstractCollectionFieldValueChanger valueChanger;
        private Collection value1;
        private Collection value2;
        private boolean result;
    }

}
