package classesForTest;

import java.util.Iterator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class ObjectContainingIterator {

    private Iterator<Integer> iterator;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingIterator that = (ObjectContainingIterator) o;

        return new EqualsBuilder().append(iterator, that.iterator)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(iterator)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("iterator", iterator)
                .toString();
    }
}
