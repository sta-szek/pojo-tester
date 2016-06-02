package org.pojo.tester.instantiator;


import java.lang.reflect.Proxy;

class ProxyInstantiator extends ObjectInstantiator {

    ProxyInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> 0);
    }
}
