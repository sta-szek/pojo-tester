package matchers;

import java.util.Arrays;
import java.util.Map;
import org.mockito.ArgumentMatcher;

public class MapMatcher extends ArgumentMatcher<Map<Class<?>, Object[]>> {

    private final Class<?> expectedClass;
    private final Object[] expectedArguments;

    public MapMatcher(final Class<?> expectedClass, final Object[] expectedArguments) {
        this.expectedClass = expectedClass;
        this.expectedArguments = expectedArguments;
    }

    @Override
    public boolean matches(final Object argument) {
        final Map<Class, Object[]> actualMap = (Map<Class, Object[]>) argument;
        if (!actualMap.containsKey(expectedClass)) {
            return false;
        }
        final Object[] actualArguments = actualMap.get(expectedClass);
        return Arrays.equals(actualArguments, expectedArguments);
    }
}
