package org.pojo.tester.instantiator;

import org.pojo.tester.ObjectInstantiationException;

abstract class ObjectInstantiator {

    protected Class<?> clazz;

    ObjectInstantiator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    ObjectInstantiator(final String qualifiedClassName) {
        try {
            clazz = Class.forName(qualifiedClassName);
        } catch (final ClassNotFoundException e) {
            throw new ObjectInstantiationException(qualifiedClassName, e);
        }
    }

    public abstract Object instantiate();


}
