package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.Optional;


public abstract class UserObjectInstantiator extends AbstractObjectInstantiator {

    public UserObjectInstantiator(final Class<?> clazz,
                           final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        return tryToInstantiate()
                .orElseThrow(this::createObjectInstantiationException);
    }

    public abstract Optional<Object> tryToInstantiate();

    private ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz, "There is no declared object for class " + clazz.getName());
    }
}
