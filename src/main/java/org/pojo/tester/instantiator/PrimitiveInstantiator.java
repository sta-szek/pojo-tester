package org.pojo.tester.instantiator;


class PrimitiveInstantiator extends ObjectInstantiator {

    PrimitiveInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        if (isBoolean(clazz)) {
            return false;
        } else {
            return 0;
        }
    }

    private boolean isBoolean(final Class<?> clazz) {
        return boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz);
    }
}
