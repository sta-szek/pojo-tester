package pl.pojo.tester.internal.instantiator;

class StringClassInstantiator extends ObjectInstantiator {

    StringClassInstantiator() {
        super(String.class);
    }

    @Override
    public Object instantiate() {
        return "www.pojo.pl";
    }
}
