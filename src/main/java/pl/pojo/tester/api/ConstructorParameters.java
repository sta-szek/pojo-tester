package pl.pojo.tester.api;

import lombok.Getter;
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
@Getter
public class ConstructorParameters {

    private final Object[] constructorParameters;
    private final Class<?>[] constructorParametersTypes;

    /**
     * Instantaites {@code ConstructorParameters} with given constructor parameters and constructor parameter's types.
     *
     * @param constructorParameters      constructor parameters
     * @param constructorParametersTypes constructor parameter's types
     */
    public ConstructorParameters(final Object[] constructorParameters, final Class<?>[] constructorParametersTypes) {
        this.constructorParameters = constructorParameters;
        this.constructorParametersTypes = constructorParametersTypes;
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

        return new EqualsBuilder().append(constructorParameters, that.constructorParameters)
                                  .append(constructorParametersTypes, that.constructorParametersTypes)
                                  .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(constructorParameters)
                                    .append(constructorParametersTypes)
                                    .toHashCode();
    }

    public boolean matches(final Class<?>[] parameterTypes) {
        return Arrays.equals(constructorParametersTypes, parameterTypes);
    }
}
