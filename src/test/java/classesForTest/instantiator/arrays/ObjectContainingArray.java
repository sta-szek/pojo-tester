package classesForTest.instantiator.arrays;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
public class ObjectContainingArray {

    private int[] array;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingArray that = (ObjectContainingArray) o;

        return new EqualsBuilder().append(array, that.array)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(array)
                                    .toHashCode();
    }
}
