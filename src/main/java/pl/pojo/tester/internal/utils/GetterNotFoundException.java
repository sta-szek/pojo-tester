package pl.pojo.tester.internal.utils;

import java.lang.reflect.Field;


public class GetterNotFoundException extends RuntimeException {

    GetterNotFoundException(final Class<?> clazz, final Field field) {
        super(String.format("Class %s has no getter for field %s", clazz.getCanonicalName(), field));
    }
}
