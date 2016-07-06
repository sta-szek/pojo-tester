package org.pojo.tester.field.collections.map;


import java.lang.reflect.Field;
import java.util.Map;
import org.pojo.tester.field.AbstractFieldValueChanger;

public abstract class AbstractMapFieldValueChanger<T extends Map> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new HashMapValueChanger().attachNext(new HashtableValueChanger())
                                                                                      .attachNext(new LinkedHashMapValueChanger())
                                                                                      .attachNext(new MapValueChanger())
                                                                                      .attachNext(new SortedMapValueChanger())
                                                                                      .attachNext(new TreeMapValueChanger());

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null || haveDifferentSizes(sourceValue, targetValue)) {
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

    private boolean haveDifferentSizes(final T sourceValue, final T targetValue) {
        return sourceValue.size() != targetValue.size();
    }
}
