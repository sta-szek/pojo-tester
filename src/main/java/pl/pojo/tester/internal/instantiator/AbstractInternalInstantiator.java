package pl.pojo.tester.internal.instantiator;

import pl.pojo.tester.api.AbstractObjectInstantiator;

abstract class AbstractInternalInstantiator extends AbstractObjectInstantiator {

    AbstractInternalInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    abstract boolean canInstantiate();

    @Override
    public String toString() {
        return "AbstractInternalInstantiator{clazz=" + clazz + '}';
    }
}
