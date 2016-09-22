package pl.pojo.tester.internal.instantiator;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javassist.util.proxy.ProxyFactory;

class ProxyInstantiator extends ObjectInstantiator {

    private final ProxyFactory proxyFactory = new ProxyFactory();

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
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this::createInvocationHandler);
    }

    private Object proxyByJavassist() {
        try {
            proxyFactory.setSuperclass(clazz);
            return proxyFactory.create(new Class[0], new Class[0], this::createMethodHandler);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ObjectInstantiationException(clazz, e.getMessage(), e);
        }
    }

    private Object createMethodHandler(final Object self, final Method thisMethod, final Method proceed, final Object[] args) {
        return createInvocationHandler(self, thisMethod, args);
    }

    private Object createInvocationHandler(final Object proxy, final Method method, final Object[] args) {
        try {
            return method.invoke(proxy, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            final Class<?> returnType = method.getReturnType();
            if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
                return true;
            } else {
                return 0;
            }
        }
    }
}
