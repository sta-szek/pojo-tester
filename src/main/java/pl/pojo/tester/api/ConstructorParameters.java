package pl.pojo.tester.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@AllArgsConstructor
@Getter
public class ConstructorParameters {

    private Object[] constructorParameters;
    private Class<?>[] constructorParametersTypes;

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
