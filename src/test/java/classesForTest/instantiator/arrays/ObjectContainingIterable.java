package classesForTest.instantiator.arrays;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ToString
public class ObjectContainingIterable {

    private Iterable<Integer> iterable;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingIterable that = (ObjectContainingIterable) o;

        return new EqualsBuilder().append(iterable, that.iterable)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(iterable)
                                    .toHashCode();
    }
}
