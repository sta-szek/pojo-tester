package org.pojo.tester.instantiator;


import java.util.HashMap;
import java.util.Map;

class PrimitiveInstantiator extends ObjectInstantiator {

    private static final Map<String, Object> preparedObjects = new HashMap<>();

    static {
        preparedObjects.put("java.lang.Boolean", Boolean.FALSE);
        preparedObjects.put("java.lang.Byte", Byte.MAX_VALUE);
        preparedObjects.put("java.lang.Character", Character.MAX_VALUE);
        preparedObjects.put("java.lang.Double", Double.MAX_VALUE);
        preparedObjects.put("java.lang.Float", Float.MAX_VALUE);
        preparedObjects.put("java.lang.Integer", Integer.MAX_VALUE);
        preparedObjects.put("java.lang.Long", Long.MAX_VALUE);
        preparedObjects.put("java.lang.Short", Short.MAX_VALUE);
        preparedObjects.put("boolean", Boolean.FALSE);
        preparedObjects.put("byte", Byte.MAX_VALUE);
        preparedObjects.put("char", Character.MAX_VALUE);
        preparedObjects.put("double", Double.MAX_VALUE);
        preparedObjects.put("float", Float.MAX_VALUE);
        preparedObjects.put("int", Integer.MAX_VALUE);
        preparedObjects.put("long", Long.MAX_VALUE);
        preparedObjects.put("short", Short.MAX_VALUE);
    }

    PrimitiveInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        final String canonicalName = clazz.getCanonicalName();
        return preparedObjects.get(canonicalName);
    }

}
