package classesForTest;

import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

@ToString
public final class ClassContainingPrivateEnum {

    private enum Status {
        OK, OK2, OK3
    }

    private final UUID a;
    private final UUID b;
    private final Status c;
    private final String d;

    private ClassContainingPrivateEnum(final UUID a, final UUID b, final Status c, final String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ClassContainingPrivateEnum that = (ClassContainingPrivateEnum) obj;

        return new EqualsBuilder().append(a, that.a)
                                  .append(b, that.b)
                                  .append(c, that.c)
                                  .append(d, that.d)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(a)
                                    .append(b)
                                    .append(c)
                                    .append(d)
                                    .toHashCode();
    }
}