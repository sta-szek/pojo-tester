package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Instantiable {

    private static final List<Class<? extends AbstractObjectInstantiator>> INSTANTIATORS;

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
    }

    private Instantiable() {
    }

    static Object[] instantiateClasses(final Class<?>[] classes,
                                       final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return Arrays.stream(classes)
                     .map(clazz -> Instantiable.forClass(clazz, constructorParameters))
                     .map(AbstractObjectInstantiator::instantiate)
                     .toArray();
    }

    static AbstractObjectInstantiator forClass(final Class<?> clazz,
                                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return instantiateInstantiators(clazz, constructorParameters).stream()
                                                                     .filter(AbstractObjectInstantiator::canInstantiate)
                                                                     .findAny()
                                                                     .get();
    }

    private static List<AbstractObjectInstantiator> instantiateInstantiators(final Class<?> clazz,
                                                                             final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        final List<AbstractObjectInstantiator> instantiators = new ArrayList<>();
        try {
            for (final Class<? extends AbstractObjectInstantiator> instantiator : INSTANTIATORS) {
                final Constructor<? extends AbstractObjectInstantiator> constructor =
                        instantiator.getDeclaredConstructor(Class.class, MultiValuedMap.class);
                constructor.setAccessible(true);
                final AbstractObjectInstantiator abstractObjectInstantiator = constructor.newInstance(clazz,
                                                                                                      constructorParameters);
                instantiators.add(abstractObjectInstantiator);
            }
        } catch (final Exception e) {
            throw new RuntimeException("Cannot load instantiators form pl.pojo.tester.internal.instantiator package.",
                                       e);
        }
        return instantiators;
    }

}
