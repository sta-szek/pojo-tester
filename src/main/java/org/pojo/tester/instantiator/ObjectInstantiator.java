package org.pojo.tester.instantiator;

abstract class ObjectInstantiator {

    protected Class<?> clazz;

    ObjectInstantiator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract Object instantiate();

}
