package pl.pojo.tester.internal.utils;

import java.lang.reflect.Field;

public class SetterNotFoundException extends RuntimeException {

    SetterNotFoundException(final Class<?> clazz, final Field field) {
        super(String.format("Class %s has no setter for field %s", clazz.getCanonicalName(), field));
    }
}
