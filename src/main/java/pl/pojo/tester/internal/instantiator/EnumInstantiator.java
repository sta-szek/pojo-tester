package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

class EnumInstantiator extends AbstractObjectInstantiator {

    EnumInstantiator(final Class<?> clazz,
                     final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        if (clazz == Enum.class) {
            return RoundingMode.UP;
        }
        final Object[] enumConstants = clazz.getEnumConstants();
        final int length = enumConstants.length;

        if (length != 0) {
            final int random = ThreadLocalRandom.current().nextInt(length);
            return enumConstants[random];
        }
        return null;
    }

    @Override
    public boolean canInstantiate() {
        return clazz.isEnum() || clazz == Enum.class;
    }
}
