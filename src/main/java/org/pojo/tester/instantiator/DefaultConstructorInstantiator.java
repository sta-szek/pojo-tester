package org.pojo.tester.instantiator;


import org.pojo.tester.ObjectInstantiationException;

class DefaultConstructorInstantiator extends ObjectInstantiator {

    DefaultConstructorInstantiator(final String qualifiedClassName) {
        super(qualifiedClassName);
    }

    DefaultConstructorInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectInstantiationException(clazz, e);
        }
    }
}
