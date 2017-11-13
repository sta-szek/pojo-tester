package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

abstract class AbstractObjectInstantiator {

    protected final Class<?> clazz;
    protected final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters;

    AbstractObjectInstantiator(final Class<?> clazz,
                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        this.clazz = clazz;
        this.constructorParameters = constructorParameters;
    }

    public abstract Object instantiate();

    public abstract boolean canInstantiate();
}
