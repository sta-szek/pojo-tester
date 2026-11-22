package pl.pojo.tester.internal.instantiator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Instantiator {

    public static final Instantiator INSTANCE = new Instantiator();

    static final List<Class<? extends AbstractObjectInstantiator>> INSTANTIATORS =
            Collections.synchronizedList(new LinkedList<>());
    private static final int INTERNAL_INSTANTIATORS_NUMBER;

    static {
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

    private Instantiator() {
    }

    public Instantiator attach(final Class<? extends AbstractObjectInstantiator> clazz) {
        synchronized (INSTANTIATORS) {
            INSTANTIATORS.add(INSTANTIATORS.size() - INTERNAL_INSTANTIATORS_NUMBER, clazz);
        }
        return this;
    }

    public Instantiator detach(final Class<? extends AbstractObjectInstantiator> clazz) {
        synchronized (INSTANTIATORS) {
            final int userCount = INSTANTIATORS.size() - INTERNAL_INSTANTIATORS_NUMBER;
            for (int i = 0; i < userCount; i++) {
                if (INSTANTIATORS.get(i) == clazz) {
                    INSTANTIATORS.remove(i);
                    break;
                }
            }
        }
        return this;
    }

    static List<Class<? extends AbstractObjectInstantiator>> snapshot() {
        synchronized (INSTANTIATORS) {
            return new ArrayList<>(INSTANTIATORS);
        }
    }
}
