package pl.pojo.tester.internal.field.collections.map;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Map;

public abstract class AbstractMapFieldValueChanger<T extends Map> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new HashMapValueChanger().attachNext(new HashtableValueChanger())
                                                                                      .attachNext(new LinkedHashMapValueChanger())
                                                                                      .attachNext(new MapValueChanger())
                                                                                      .attachNext(new SortedMapValueChanger())
                                                                                      .attachNext(new TreeMapValueChanger());

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
