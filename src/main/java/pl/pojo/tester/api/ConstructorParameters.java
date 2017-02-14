package pl.pojo.tester.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

/**
 * Defines constructor parameters and constructor parameter's types.
 * <p>
 * Constructor parameters's types are used to select constructor.
 * <p>
 * Constructor parameters are passed to selected constructor
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class ConstructorParameters {

    private final Object[] parameters;
    private final Class<?>[] parametersTypes;

    /**
     * Instantiates {@code ConstructorParameters} with given constructor parameters and constructor parameter's types.
     *
     * @param parameters      constructor parameters
     * @param parametersTypes constructor parameter's types
     */
    public ConstructorParameters(final Object[] parameters, final Class<?>[] parametersTypes) {
        this.parameters = Arrays.copyOf(parameters, parameters.length);
        this.parametersTypes = Arrays.copyOf(parametersTypes, parametersTypes.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ConstructorParameters that = (ConstructorParameters) o;

        return new EqualsBuilder().append(parameters, that.parameters)
                                  .append(parametersTypes, that.parametersTypes)
                                  .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(parameters)
                                    .append(parametersTypes)
                                    .toHashCode();
    }

    public boolean matches(final Class<?>[] parameterTypes) {
        return Arrays.equals(parametersTypes, parameterTypes);
    }

    public Class<?>[] getParametersTypes() {
        return parametersTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }
}
