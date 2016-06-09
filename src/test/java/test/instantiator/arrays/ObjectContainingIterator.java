package test.instantiator.arrays;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Iterator;

@ToString
public class ObjectContainingIterator {

    private Iterator<Integer> interator;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingIterator that = (ObjectContainingIterator) o;

        return new EqualsBuilder().append(interator, that.interator)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(interator)
                                    .toHashCode();
    }
}
