package classesForTest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


class ObjectContainingList {

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("list", list)
                .toString();
    }
}
