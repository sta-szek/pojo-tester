package org.pojo.tester.instantiator;

class NewInstanceGenerator {

    public NewInstanceGenerator() {
    }

    public Object createNewInstance(final Class<?> clazz) {
        return Instantiable.forClass(clazz)
                           .instantiate();
    }
}
