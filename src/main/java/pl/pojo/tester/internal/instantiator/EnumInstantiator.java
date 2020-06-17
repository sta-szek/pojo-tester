package pl.pojo.tester.internal.instantiator;


import java.util.Random;

class EnumInstantiator extends AbstractInternalInstantiator {

    EnumInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        final Object[] enumConstants = clazz.getEnumConstants();
        final int length = enumConstants.length;

        if (length != 0) {
            final int random = new Random().nextInt(length);
            return enumConstants[random];
        }
        return null;
    }

    @Override
    public boolean canInstantiate() {
        return clazz.isEnum();
    }
}
