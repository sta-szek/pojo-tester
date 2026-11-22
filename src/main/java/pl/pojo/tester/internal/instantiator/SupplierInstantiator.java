package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.function.Supplier;

final class SupplierInstantiator extends AbstractObjectInstantiator {

    private final Supplier<?> supplier;

    SupplierInstantiator(final Class<?> clazz,
                         final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters,
                         final Supplier<?> supplier) {
        super(clazz, constructorParameters);
        this.supplier = supplier;
    }

    @Override
    public Object instantiate() {
        final Object instance = supplier.get();
        if (instance == null) {
            throw new ObjectInstantiationException(clazz, "Supplier for " + clazz.getName() + " returned null.");
        }
        return instance;
    }

    @Override
    public boolean canInstantiate() {
        return true;
    }
}
