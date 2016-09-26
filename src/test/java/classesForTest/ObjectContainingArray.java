package classesForTest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("array", array)
                .toString();
    }
}
