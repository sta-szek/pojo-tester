package pl.pojo.tester.internal.assertion.constructor;

import pl.pojo.tester.internal.assertion.AssertionError;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;


public class ConstructorAssertionError extends AssertionError {

    private static final String INSTANTIATE_EXCEPTION = "Constructor:\n"
                                                        + "%s\n"
                                                        + "of class:\n"
                                                        + "%s\n"
                                                        + "could not create instance with parameters:\n"
                                                        + "%s\n"
                                                        + "Root cause is:\n"
                                                        + "%s";
    private final Constructor<?> constructorUnderAssert;
    private final Object[] constructorParameters;
    private final ReflectiveOperationException e;

    ConstructorAssertionError(final Class<?> classUnderTest,
                              final Constructor<?> constructorUnderAssert,
                              final Object[] constructorParameters,
                              final ReflectiveOperationException e) {
        super(classUnderTest);
        this.constructorUnderAssert = constructorUnderAssert;
        this.constructorParameters = constructorParameters;
        this.e = e;
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'constructor' method implementation.", testedCass.getCanonicalName());
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(INSTANTIATE_EXCEPTION,
                             constructorUnderAssert,
                             testedCass,
                             createArrayContentString(constructorParameters),
                             e.getMessage());
    }

    private String createArrayContentString(final Object[] array) {
        if (array == null) {
            return "<no parameters>";
        }
        return Arrays.stream(array)
                     .map(String::valueOf)
                     .collect(Collectors.joining(", ", "[ ", " ]"));
    }
}
