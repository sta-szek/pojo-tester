package pl.pojo.tester.internal.instantiator;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

final class InstantiatorSuppliers {

    private static final ThreadLocal<Map<Class<?>, Supplier<?>>> CURRENT = new ThreadLocal<>();

    private InstantiatorSuppliers() {
    }

    static <T> T call(final Map<Class<?>, Supplier<?>> suppliers, final Supplier<T> action) {
        final Map<Class<?>, Supplier<?>> previous = CURRENT.get();
        CURRENT.set(suppliers == null ? Collections.emptyMap() : suppliers);
        try {
            return action.get();
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }

    static boolean isActive() {
        return CURRENT.get() != null;
    }

    static Supplier<?> forClass(final Class<?> clazz) {
        final Map<Class<?>, Supplier<?>> suppliers = CURRENT.get();
        if (suppliers == null) {
            return null;
        }
        return suppliers.get(clazz);
    }
}
