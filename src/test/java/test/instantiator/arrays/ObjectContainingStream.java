package test.instantiator.arrays;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.stream.Stream;

@ToString
public class ObjectContainingStream {

    private Stream<Integer> stream;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingStream that = (ObjectContainingStream) o;

        return new EqualsBuilder().append(stream, that.stream)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stream)
                                    .toHashCode();
    }
}
