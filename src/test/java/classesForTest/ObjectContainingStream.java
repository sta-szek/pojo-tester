package classesForTest;

import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("stream", stream)
                .toString();
    }
}
