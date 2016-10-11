package pl.pojo.tester.internal.instantiator;


import java.util.HashMap;
import java.util.Map;

class PrimitiveInstantiator extends ObjectInstantiator {

    private static final Map<String, Object> PREPARED_OBJECTS = new HashMap<>();

    static {
        PREPARED_OBJECTS.put("java.lang.Boolean", Boolean.FALSE);
        PREPARED_OBJECTS.put("java.lang.Byte", Byte.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Character", Character.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Double", Double.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Float", Float.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Integer", Integer.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Long", Long.MAX_VALUE);
        PREPARED_OBJECTS.put("java.lang.Short", Short.MAX_VALUE);
        PREPARED_OBJECTS.put("boolean", Boolean.FALSE);
        PREPARED_OBJECTS.put("byte", Byte.MAX_VALUE);
        PREPARED_OBJECTS.put("char", Character.MAX_VALUE);
        PREPARED_OBJECTS.put("double", Double.MAX_VALUE);
        PREPARED_OBJECTS.put("float", Float.MAX_VALUE);
        PREPARED_OBJECTS.put("int", Integer.MAX_VALUE);
        PREPARED_OBJECTS.put("long", Long.MAX_VALUE);
        PREPARED_OBJECTS.put("short", Short.MAX_VALUE);
    }

    PrimitiveInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        final String canonicalName = clazz.getCanonicalName();
        return PREPARED_OBJECTS.get(canonicalName);
    }

}
