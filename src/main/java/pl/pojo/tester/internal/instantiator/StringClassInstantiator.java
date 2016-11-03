package pl.pojo.tester.internal.instantiator;

class StringClassInstantiator extends AbstractObjectInstantiator {

    StringClassInstantiator() {
        super(String.class);
    }

    @Override
    public Object instantiate() {
        return "www.pojo.pl";
    }
}
