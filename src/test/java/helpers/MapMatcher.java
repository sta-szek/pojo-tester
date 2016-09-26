package helpers;

import java.util.Arrays;
import java.util.Map;
import org.mockito.ArgumentMatcher;
import pl.pojo.tester.api.ConstructorParameters;

public class MapMatcher implements ArgumentMatcher<Map<Class<?>, ConstructorParameters>> {

    private final Class<?> expectedClass;
    private final ConstructorParameters expectedArguments;

    public MapMatcher(final Class<?> expectedClass, final ConstructorParameters expectedArguments) {
        this.expectedClass = expectedClass;
        this.expectedArguments = expectedArguments;
    }


    @Override
    public boolean matches(final Map<Class<?>, ConstructorParameters> argument) {
        if (!argument.containsKey(expectedClass)) {
            return false;
        }
        final ConstructorParameters actualArguments = argument.get(expectedClass);
        return Arrays.equals(actualArguments.getConstructorParameters(), expectedArguments.getConstructorParameters()) &&
               Arrays.equals(actualArguments.getConstructorParametersTypes(), expectedArguments.getConstructorParametersTypes());
    }
}
