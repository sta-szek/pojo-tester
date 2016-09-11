package pl.pojo.tester.field.collections.map;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.fields.collections.map.Maps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class AbstractMapFieldValueChangerTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(new HashtableValueChanger(), Maps.class.getDeclaredField("hashtable"), true),
                         new CanChangeCase(new LinkedHashMapValueChanger(), Maps.class.getDeclaredField("linkedHashMap"), true),
                         new CanChangeCase(new MapValueChanger(), Maps.class.getDeclaredField("map"), true),
                         new CanChangeCase(new SortedMapValueChanger(), Maps.class.getDeclaredField("sortedMap"), true),
                         new CanChangeCase(new TreeMapValueChanger(), Maps.class.getDeclaredField("treeMap"), true),
                         new CanChangeCase(new HashMapValueChanger(), Maps.class.getDeclaredField("hashMap"), true))
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
        final Map<String, String> mapABC = new HashMap<>();
        mapABC.put("A", "A");
        mapABC.put("B", "B");
        mapABC.put("C", "C");

        final Map<String, String> mapAB = new HashMap<>();
        mapAB.put("A", "A");
        mapAB.put("B", "B");

        final Map<String, String> mapAC = new HashMap<>();
        mapAC.put("A", "A");
        mapAC.put("C", "C");

        final LinkedHashMap linkedHashMap = new LinkedHashMap<>(mapABC);

        return Stream.of(new AreDifferentCase(new HashtableValueChanger(), null, null, false),
                         new AreDifferentCase(new HashtableValueChanger(), new Hashtable<>(), new Hashtable<>(), false),
                         new AreDifferentCase(new HashtableValueChanger(), new Hashtable<>(mapABC), new Hashtable<>(mapABC), false),
                         new AreDifferentCase(new HashtableValueChanger(), new Hashtable<>(), null, true),
                         new AreDifferentCase(new HashtableValueChanger(), new Hashtable<>(mapAB), new Hashtable<>(mapABC), true),
                         new AreDifferentCase(new HashtableValueChanger(), new Hashtable<>(mapAB), new Hashtable<>(mapAC), true),
                         new AreDifferentCase(new LinkedHashMapValueChanger(), null, null, false),
                         new AreDifferentCase(new LinkedHashMapValueChanger(), new LinkedHashMap<>(), new LinkedHashMap<>(), false),
                         new AreDifferentCase(new LinkedHashMapValueChanger(), linkedHashMap, linkedHashMap, false),
                         new AreDifferentCase(new LinkedHashMapValueChanger(), null, new LinkedHashMap<>(), true),
                         new AreDifferentCase(new LinkedHashMapValueChanger(), new LinkedHashMap<>(mapAB), linkedHashMap, true),
                         new AreDifferentCase(new MapValueChanger(), null, null, false),
                         new AreDifferentCase(new MapValueChanger(), new HashMap<>(), new HashMap<>(), false),
                         new AreDifferentCase(new MapValueChanger(), new HashMap<>(mapABC), new HashMap<>(mapABC), false),
                         new AreDifferentCase(new MapValueChanger(), null, new HashMap<>(), true),
                         new AreDifferentCase(new MapValueChanger(), new HashMap<>(mapAB), new HashMap<>(mapABC), true),
                         new AreDifferentCase(new SortedMapValueChanger(), null, null, false),
                         new AreDifferentCase(new SortedMapValueChanger(), new TreeMap<>(), new TreeMap<>(), false),
                         new AreDifferentCase(new SortedMapValueChanger(), new TreeMap<>(mapABC), new TreeMap<>(mapABC), false),
                         new AreDifferentCase(new SortedMapValueChanger(), null, new TreeMap<>(), true),
                         new AreDifferentCase(new SortedMapValueChanger(), new TreeMap<>(mapAB), new TreeMap<>(mapABC), true),
                         new AreDifferentCase(new TreeMapValueChanger(), null, null, false),
                         new AreDifferentCase(new TreeMapValueChanger(), new TreeMap<>(), new TreeMap<>(), false),
                         new AreDifferentCase(new TreeMapValueChanger(), new TreeMap<>(mapABC), new TreeMap<>(mapABC), false),
                         new AreDifferentCase(new TreeMapValueChanger(), null, new TreeMap<>(), true),
                         new AreDifferentCase(new TreeMapValueChanger(), new TreeMap<>(mapAB), new TreeMap<>(mapABC), true),
                         new AreDifferentCase(new HashMapValueChanger(), null, null, false),
                         new AreDifferentCase(new HashMapValueChanger(), new HashMap<>(), new HashMap<>(), false),
                         new AreDifferentCase(new HashMapValueChanger(), new HashMap<>(mapABC), new HashMap<>(mapABC), false),
                         new AreDifferentCase(new HashMapValueChanger(), null, new HashMap<>(), true),
                         new AreDifferentCase(new HashMapValueChanger(), new HashMap<>(mapAB), new HashMap<>(mapABC), true))
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
        private AbstractMapFieldValueChanger valueChanger;
        private Field field;
        private boolean result;
    }

    @AllArgsConstructor
    private class AreDifferentCase {
        private AbstractMapFieldValueChanger valueChanger;
        private Map value1;
        private Map value2;
        private boolean result;
    }
}
