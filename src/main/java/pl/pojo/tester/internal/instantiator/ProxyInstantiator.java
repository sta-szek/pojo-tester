package pl.pojo.tester.internal.instantiator;


import javassist.util.proxy.ProxyFactory;
import org.apache.commons.collections4.MultiValuedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;


class ProxyInstantiator extends AbstractMultiConstructorInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestConstructorInstantiator.class);

    private final ProxyFactory proxyFactory = new ProxyFactory();

    ProxyInstantiator(final Class<?> clazz,
                      final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        Object result = instantiateUsingUserParameters();
        if (result == null) {
            if (clazz.isAnnotation() || clazz.isInterface()) {
                result = proxyByJava();
            } else {
                proxyFactory.setSuperclass(clazz);
                result = createFindingBestConstructor();
            }
        }
        return result;
    }

    @Override
    public boolean canInstantiate() {
        return qualifiesForProxy(clazz);
    }

    private boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }

    private Object proxyByJava() {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{ clazz }, this::createInvocationHandler);
    }

    @Override
    protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, final Object[] parameters) {
        try {
            final Class proxyClass = proxyFactory.createClass();
            final Constructor declaredConstructor = proxyClass.getDeclaredConstructor(parameterTypes);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(parameters);
        } catch (final ReflectiveOperationException e) {
            throw new ObjectInstantiationException(clazz, "Could not create object from args constructor", e);
        }
    }

    @Override
    protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
        try {
            return proxyFactory.create(new Class[0], new Class[0]);
        } catch (final InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException e) {
            LOGGER.debug("Exception:", e);
            // ignore, we want to try all constructors
            // if all constructors fail, it will be handled by caller
            return null;
        }
    }

    private Object createInvocationHandler(final Object proxy, final Method method, final Object[] args) {
        try {
            method.setAccessible(true);
            return method.invoke(proxy, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.debug("Exception:", e);
            final Class<?> returnType = method.getReturnType();
            if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
                return true;
            } else if (returnType.equals(String.class)) {
                return "string";
            } else {
                return 0;
            }
        }
    }

    @Override
    protected ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Class could not be created by any constructor (using ProxyInstantiator).");
    }

}
