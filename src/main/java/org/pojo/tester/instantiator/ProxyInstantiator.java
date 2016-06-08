package org.pojo.tester.instantiator;


import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

class ProxyInstantiator extends ObjectInstantiator {

    private final ProxyFactory proxyFactory = new ProxyFactory();

    ProxyInstantiator(final String qualifiedClassName) {
        super(qualifiedClassName);
    }

    ProxyInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        if (clazz.isAnnotation() || clazz.isInterface()) {
            return proxyByJava();
        } else {
            return proxyByJavassist();
        }
    }

    private Object proxyByJava() {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> 0);
    }

    private Object proxyByJavassist() {
        try {
            proxyFactory.setSuperclass(clazz);
            return proxyFactory.create(new Class[0], new Class[0], (self, thisMethod, proceed, args) -> 0);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ObjectInstantiationException(clazz, e);
        }
    }
}
