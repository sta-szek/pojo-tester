package test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TestHelper {

    public static List<Field> getAllFieldsExceptDummyJacocoField(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> !field.getName()
                                            .equals("$jacocoData"))
                     .collect(Collectors.toList());
    }

    public static String getDefaultDisplayName(final Object values) {
        return "------> " + String.valueOf(values) + " <------";
    }
}
