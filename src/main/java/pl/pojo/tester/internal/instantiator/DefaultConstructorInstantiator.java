package pl.pojo.tester.internal.instantiator;


class DefaultConstructorInstantiator extends ObjectInstantiator {

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
