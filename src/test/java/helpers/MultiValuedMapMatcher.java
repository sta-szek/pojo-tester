package helpers;

import org.apache.commons.collections4.MultiValuedMap;
import org.mockito.ArgumentMatcher;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.Arrays;

public class MultiValuedMapMatcher implements ArgumentMatcher<MultiValuedMap<Class<?>, ConstructorParameters>> {

    private final Class<?> expectedClass;
    private final ConstructorParameters expectedArguments;

    public MultiValuedMapMatcher(final Class<?> expectedClass, final ConstructorParameters expectedArguments) {
        this.expectedClass = expectedClass;
        this.expectedArguments = expectedArguments;
    }


    @Override
    public boolean matches(final MultiValuedMap<Class<?>, ConstructorParameters> argument) {
        if (!argument.containsKey(expectedClass)) {
            return false;
        }
        return argument.get(expectedClass)
                       .stream()
                       .map(actualArgument -> Arrays.equals(actualArgument.getConstructorParameters(), expectedArguments.getConstructorParameters())
                                              &&
                                              Arrays.equals(actualArgument.getConstructorParametersTypes(), expectedArguments.getConstructorParametersTypes()))
                       .findAny()
                       .isPresent();
    }
}
