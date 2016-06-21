package org.pojo.tester.field.collections.map;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.fields.collections.map.Maps;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class AbstractMapFieldValueChangerTest {

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final AbstractMapFieldValueChanger changer,
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
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AbstractMapFieldValueChanger changer,
                                                                                final Map value1,
                                                                                final Map value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = changer.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        final Map mapABC = new HashMap<>();
        mapABC.put("A", "A");
        mapABC.put("B", "B");
        mapABC.put("C", "C");

        final Map mapAB = new HashMap<>();
        mapAB.put("A", "A");
        mapAB.put("B", "B");

        return new Object[][]{
                {new HashtableValueChanger(), null, null, false},
                {new HashtableValueChanger(), new Hashtable<>(), new Hashtable<>(), false},
                {new HashtableValueChanger(), new Hashtable<>(mapABC), new Hashtable<>(mapABC), false},
                {new HashtableValueChanger(), null, new Hashtable<>(), true},
                {new HashtableValueChanger(), new Hashtable<>(mapAB), new Hashtable<>(mapABC), true},

                {new LinkedHashMapValueChanger(), null, null, false},
                {new LinkedHashMapValueChanger(), new LinkedHashMap<>(), new LinkedHashMap<>(), false},
                {new LinkedHashMapValueChanger(), new LinkedHashMap<>(mapABC), new LinkedHashMap<>(mapABC), false},
                {new LinkedHashMapValueChanger(), null, new LinkedHashMap<>(), true},
                {new LinkedHashMapValueChanger(), new LinkedHashMap<>(mapAB), new LinkedHashMap<>(mapABC), true},

                {new MapValueChanger(), null, null, false},
                {new MapValueChanger(), new HashMap<>(), new HashMap<>(), false},
                {new MapValueChanger(), new HashMap<>(mapABC), new HashMap<>(mapABC), false},
                {new MapValueChanger(), null, new HashMap<>(), true},
                {new MapValueChanger(), new HashMap<>(mapAB), new HashMap<>(mapABC), true},

                {new SortedMapValueChanger(), null, null, false},
                {new SortedMapValueChanger(), new TreeMap<>(), new TreeMap<>(), false},
                {new SortedMapValueChanger(), new TreeMap<>(mapABC), new TreeMap<>(mapABC), false},
                {new SortedMapValueChanger(), null, new TreeMap<>(), true},
                {new SortedMapValueChanger(), new TreeMap<>(mapAB), new TreeMap<>(mapABC), true},

                {new TreeMapValueChanger(), null, null, false},
                {new TreeMapValueChanger(), new TreeMap<>(), new TreeMap<>(), false},
                {new TreeMapValueChanger(), new TreeMap<>(mapABC), new TreeMap<>(mapABC), false},
                {new TreeMapValueChanger(), null, new TreeMap<>(), true},
                {new TreeMapValueChanger(), new TreeMap<>(mapAB), new TreeMap<>(mapABC), true},

                {new HashMapValueChanger(), null, null, false},
                {new HashMapValueChanger(), new HashMap<>(), new HashMap<>(), false},
                {new HashMapValueChanger(), new HashMap<>(mapABC), new HashMap<>(mapABC), false},
                {new HashMapValueChanger(), null, new HashMap<>(), true},
                {new HashMapValueChanger(), new HashMap<>(mapAB), new HashMap<>(mapABC), true},
                };
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field hashtable = Maps.class.getDeclaredField("hashtable");
        final Field linkedHashMap = Maps.class.getDeclaredField("linkedHashMap");
        final Field map = Maps.class.getDeclaredField("map");
        final Field sortedMap = Maps.class.getDeclaredField("sortedMap");
        final Field treeMap = Maps.class.getDeclaredField("treeMap");
        final Field hashMap = Maps.class.getDeclaredField("hashMap");

        return new Object[][]{
                {new HashtableValueChanger(), hashtable, true},
                {new LinkedHashMapValueChanger(), linkedHashMap, true},
                {new MapValueChanger(), map, true},
                {new SortedMapValueChanger(), sortedMap, true},
                {new TreeMapValueChanger(), treeMap, true},
                {new HashMapValueChanger(), hashMap, true},
                };
    }
}
