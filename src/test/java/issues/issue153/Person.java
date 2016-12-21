package issues.issue153;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person {

    private final String id;
    private final PersonType type;

    public Person(final String id, final PersonType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Person that = (Person) obj;

        return new EqualsBuilder().append(id, that.id)
                                  .append(type, that.type)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                                    .append(type)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                                        .append("type", type)
                                        .toString();
    }
}