package pl.pojo.tester.internal.instantiator;

import pl.pojo.tester.api.AbstractObjectInstantiator;

import java.util.function.Supplier;

public class SupplierInstantiator extends AbstractObjectInstantiator {

    private final Supplier<?> supplier;

    public SupplierInstantiator(final Class<?> clazz, final Supplier<?> supplier) {
        super(clazz);
        this.supplier = supplier;
    }

    @Override
    public Object instantiate() {
        return supplier.get();
    }

    @Override
    public String toString() {
        return "SupplierInstantiator{supplier=" + supplier + ", clazz=" + clazz + '}';
    }
}
