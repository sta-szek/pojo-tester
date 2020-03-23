package pl.pojo.tester.internal.instantiator;

import java.util.LinkedList;
import java.util.List;

public final class Instantiator {

    public static final Instantiator INSTANCE = new Instantiator();

    static final List<Class<? extends AbstractObjectInstantiator>> INSTANTIATORS;
    private final static int INTERNAL_INSTANTIATORS_NUMBER;

    static {
        INSTANTIATORS = new LinkedList<>();
        INSTANTIATORS.add(UserDefinedConstructorInstantiator.class);
        INSTANTIATORS.add(JavaTypeInstantiator.class);
        INSTANTIATORS.add(CollectionInstantiator.class);
        INSTANTIATORS.add(DefaultConstructorInstantiator.class);
        INSTANTIATORS.add(EnumInstantiator.class);
        INSTANTIATORS.add(ArrayInstantiator.class);
        INSTANTIATORS.add(ProxyInstantiator.class);
        INSTANTIATORS.add(BestConstructorInstantiator.class);

        INTERNAL_INSTANTIATORS_NUMBER = INSTANTIATORS.size();
    }

    public Instantiator attach(final Class<? extends AbstractObjectInstantiator> clazz) {
        INSTANTIATORS.add(INSTANTIATORS.size() - INTERNAL_INSTANTIATORS_NUMBER, clazz);
        return this;
    }
}
