package pl.pojo.tester.internal.instantiator;


import pl.pojo.tester.api.AbstractObjectInstantiator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public final class Instantiable {

    private static final List<Class<? extends AbstractObjectInstantiator>> INSTANTIATORS;

    static {
        INSTANTIATORS = new LinkedList<>();
        INSTANTIATORS.add(JavaTypeInstantiator.class);
        INSTANTIATORS.add(CollectionInstantiator.class);
        INSTANTIATORS.add(DefaultConstructorInstantiator.class);
        INSTANTIATORS.add(EnumInstantiator.class);
        INSTANTIATORS.add(ArrayInstantiator.class);
        INSTANTIATORS.add(ProxyInstantiator.class);
        INSTANTIATORS.add(BestConstructorInstantiator.class);
    }

    private Instantiable() {
    }

    static Object[] instantiateClasses(final Class<?>[] classes,
                                       final List<AbstractObjectInstantiator> predefinedInstantiators) {
        return Arrays.stream(classes)
                     .map(clazz -> Instantiable.forClass(clazz, predefinedInstantiators))
                     .map(AbstractObjectInstantiator::instantiate)
                     .toArray();
    }

    static AbstractObjectInstantiator forClass(final Class<?> clazz,
                                               final List<AbstractObjectInstantiator> predefinedInstantiators) {
        return findPredefinedInstantiator(clazz, predefinedInstantiators)
                .orElseGet(findFirstMatchingInstantiator(clazz, predefinedInstantiators));
    }

    private static Supplier<AbstractObjectInstantiator> findFirstMatchingInstantiator(final Class<?> clazz,
                                                                                      final List<AbstractObjectInstantiator> predefinedInstantiators) {
        return () -> instantiateInstantiators(clazz, predefinedInstantiators).stream()
                                                                             .filter(AbstractInternalInstantiator::canInstantiate)
                                                                             .findFirst()
                                                                             .get();
    }

    private static Optional<AbstractObjectInstantiator> findPredefinedInstantiator(final Class<?> clazz,
                                                                                   final List<AbstractObjectInstantiator> predefinedInstantiators) {
        return predefinedInstantiators.stream()
                                      .filter(instantiator -> clazz.equals(instantiator.getClazz()))
                                      .findFirst();
    }

    private static List<AbstractInternalInstantiator> instantiateInstantiators(final Class<?> clazz,
                                                                               final List<AbstractObjectInstantiator> predefinedInstantiators) {
        final List<AbstractInternalInstantiator> instantiators = new ArrayList<>();

        instantiators.add(new JavaTypeInstantiator(clazz));
        instantiators.add(new CollectionInstantiator(clazz));
        instantiators.add(new DefaultConstructorInstantiator(clazz));
        instantiators.add(new EnumInstantiator(clazz));
        instantiators.add(new ArrayInstantiator(clazz));
        instantiators.add(new ProxyInstantiator(clazz, predefinedInstantiators));
        instantiators.add(new BestConstructorInstantiator(clazz, predefinedInstantiators));

        return instantiators;
    }
}
