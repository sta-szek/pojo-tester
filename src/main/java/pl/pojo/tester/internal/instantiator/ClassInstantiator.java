package pl.pojo.tester.internal.instantiator;

class ClassInstantiator extends AbstractObjectInstantiator {

    ClassInstantiator() {
        super(Class.class);
    }

    @Override
    public Object instantiate() {
        return Class.class;
    }
}
