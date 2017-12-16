package pl.pojo.tester.internal.instantiator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.AbstractObjectInstantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class BestConstructorInstantiator extends AbstractMultiConstructorInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestConstructorInstantiator.class);

    BestConstructorInstantiator(final Class<?> clazz, final List<AbstractObjectInstantiator> instantiators) {
        super(clazz, instantiators);
    }

    @Override
    public Object instantiate() {
        return createFindingBestConstructor();
    }

    @Override
    public boolean canInstantiate() {
        return true;
    }

    @Override
    protected ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Class could not be created by any constructor (using BestConstructorInstantiator).");
    }

    @Override
    protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, final Object[] parameters) {
        try {
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(parameters);
        } catch (final ReflectiveOperationException e) {
            throw new ObjectInstantiationException(clazz,
                                                   "Could not create object from args constructor",
                                                   parameterTypes,
                                                   parameters,
                                                   e);
        }
    }

    @Override
    protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.debug("Exception:", e);
            // ignore, we want to try all constructors
            // if all constructors fail, it will be handled by caller
            return null;
        }
    }

}
