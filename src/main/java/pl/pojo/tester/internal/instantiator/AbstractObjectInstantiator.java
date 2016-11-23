package pl.pojo.tester.internal.instantiator;

abstract class AbstractObjectInstantiator {

    protected final Class<?> clazz;

    AbstractObjectInstantiator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public abstract Object instantiate();

}
