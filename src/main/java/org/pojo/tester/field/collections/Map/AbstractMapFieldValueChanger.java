package org.pojo.tester.field.collections.map;


import org.pojo.tester.field.AbstractFieldValueChanger;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class AbstractMapFieldValueChanger<T extends Map> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new HashMapValueChanger().register(new HashtableValueChanger())
                                                                                      .register(new LinkedHashMapValueChanger())
                                                                                      .register(new MapValueChanger())
                                                                                      .register(new SortedMapValueChanger())
                                                                                      .register(new TreeMapValueChanger());

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            targetValue.forEach(sourceValue::remove);
            return sourceValue.size() != 0;
        }
    }

    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(getGenericTypeClass());
    }
}
