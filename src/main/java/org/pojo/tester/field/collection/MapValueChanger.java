package org.pojo.tester.field.collection;

import java.util.Collections;
import java.util.Map;

public class MapValueChanger extends CollectionFieldValueChanger<Map<?, ?>> {

    @Override
    public boolean areDifferentValues(final Map<?, ?> sourceValue, final Map<?, ?> targetValue) {
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
    protected Map<?, ?> increaseValue(final Map<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_MAP;
    }
}
