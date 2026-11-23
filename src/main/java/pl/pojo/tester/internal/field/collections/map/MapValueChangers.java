package pl.pojo.tester.internal.field.collections.map;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public final class MapValueChangers {

    public static final AbstractFieldValueChanger INSTANCE =
            new HashMapValueChanger().attachNext(new HashtableValueChanger())
            .attachNext(new LinkedHashMapValueChanger())
            .attachNext(new MapValueChanger())
            .attachNext(new SortedMapValueChanger())
            .attachNext(new TreeMapValueChanger());

    private MapValueChangers() {
    }
}
