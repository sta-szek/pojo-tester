package pl.pojo.tester.api;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
public class ConstructorParameters {

    private final Object[] constructorParameters;
    private final Class<?>[] constructorParametersTypes;

    public ConstructorParameters(final Object[] constructorParameters, final Class<?>[] constructorParametersTypes) {
        this.constructorParameters = constructorParameters;
        this.constructorParametersTypes = constructorParametersTypes;
    }

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(constructorParameters)
                                    .append(constructorParametersTypes)
                                    .toHashCode();
    }
}
