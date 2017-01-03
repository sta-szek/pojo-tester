package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class BestConstructorInstantiator extends MultiConstructorInstantiator {


    BestConstructorInstantiator(final Class<?> clazz, final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        Object result = instantiateUsingUserParameters();
        if (result == null) {
            result = createFindingBestConstructor();
        }
        return result;
    }

    @Override
    protected ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Class could not be created by any constructor (using BestConstructorInstantiator).");
    }

    @Override
    protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, final Object[] parameters) throws Exception {
        final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
        declaredConstructor.setAccessible(true);
        return declaredConstructor.newInstance(parameters);
    }

    @Override
    protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // ignore, we want to try all constructors
            // if all constructors fail, it will be handled by caller
            return null;
        }
    }

}
