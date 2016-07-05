package org.pojo.tester;

import java.lang.reflect.Field;


public class SetterNotFoundException extends RuntimeException {

    public SetterNotFoundException(final Class<?> testedClass, final Field field) {
        super(String.format("Class %s has no setter for field %s", testedClass.getCanonicalName(), field));
    }
}
