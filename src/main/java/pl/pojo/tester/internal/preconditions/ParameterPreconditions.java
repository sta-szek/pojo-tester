package pl.pojo.tester.internal.preconditions;

import java.util.Arrays;

public final class ParameterPreconditions {

    private ParameterPreconditions() {}

    public static void checkNotBlank(final String parameterName, final String parameterValue) {
        checkNotNull(parameterName, parameterValue);
        if (hasZeroLength(parameterValue)) {
            throw new BlankParameterException(parameterName, parameterValue);
        }
    }

    public static void checkNotBlank(final String parameterName, final String[] parameterValue) {
        Arrays.stream(parameterValue)
              .forEach(each -> checkNotBlank(parameterName, each));
    }

    public static void checkNotNull(final String parameterName, final Object parameterValue) {
        if (parameterValue == null) {
            throw new NullParameterException(parameterName);
        }
    }

    public static void checkNotNull(final String parameterName, final Object[] parameterValue) {
        Arrays.stream(parameterValue)
              .forEach(each -> checkNotNull(parameterName, each));
    }

    private static boolean hasZeroLength(final String parameterValue) {
        return parameterValue.trim()
                             .length() == 0;
    }
}
