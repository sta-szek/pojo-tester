package pl.pojo.tester.instantiator;


import java.util.Random;

class EnumInstantiator extends ObjectInstantiator {

    EnumInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        final Object[] enumConstants = clazz.getEnumConstants();
        final int length = enumConstants.length;

        if (length != 0) {
            final int random = new Random().nextInt(length - 1);
            return enumConstants[random];
        }
        return null;
    }
}
