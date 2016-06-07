package org.pojo.tester.instantiator;

import java.lang.reflect.Array;

class ArrayInstantiator extends ObjectInstantiator {

    private static final int DEFAULT_ARRAY_LENGTH = 0;

    ArrayInstantiator(final String qualifiedClassName) {
        super(qualifiedClassName);
    }

    ArrayInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        return Array.newInstance(clazz.getComponentType(), DEFAULT_ARRAY_LENGTH);
    }
}
