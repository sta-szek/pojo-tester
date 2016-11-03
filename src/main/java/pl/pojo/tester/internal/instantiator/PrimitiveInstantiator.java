package pl.pojo.tester.internal.instantiator;


import java.util.HashMap;
import java.util.Map;

class PrimitiveInstantiator extends AbstractObjectInstantiator {

    private static final Map<String, Object> PREPARED_OBJECTS = new HashMap<>();

    static {
        PREPARED_OBJECTS.put("java.lang.Boolean", Boolean.FALSE);
        PREPARED_OBJECTS.put("java.lang.Byte", (byte) 1);
        PREPARED_OBJECTS.put("java.lang.Character", 'a');
        PREPARED_OBJECTS.put("java.lang.Double", 2.5D);
        PREPARED_OBJECTS.put("java.lang.Float", 3.5F);
        PREPARED_OBJECTS.put("java.lang.Integer", 4);
        PREPARED_OBJECTS.put("java.lang.Long", 5L);
        PREPARED_OBJECTS.put("java.lang.Short", (short) 6);
        PREPARED_OBJECTS.put("boolean", Boolean.TRUE);
        PREPARED_OBJECTS.put("byte", (byte) -1);
        PREPARED_OBJECTS.put("char", 'b');
        PREPARED_OBJECTS.put("double", -2.5D);
        PREPARED_OBJECTS.put("float", -3.5F);
        PREPARED_OBJECTS.put("int", -4);
        PREPARED_OBJECTS.put("long", -5L);
        PREPARED_OBJECTS.put("short", (short) -6);
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
