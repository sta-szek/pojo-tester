package pl.pojo.tester.api;

import java.lang.reflect.Field;


public class GetterNotFoundException extends RuntimeException {

    public GetterNotFoundException(final Class<?> testedClass, final Field field) {
        super(String.format("Class %s has no getter for field %s", testedClass.getCanonicalName(), field));
    }
}
