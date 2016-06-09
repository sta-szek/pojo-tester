package test.instantiator.arrays;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@ToString
public class ObjectContainingList {

    private List<Integer> list;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ObjectContainingList that = (ObjectContainingList) o;

        return new EqualsBuilder().append(list, that.list)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(list)
                                    .toHashCode();
    }
}
