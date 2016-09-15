package classesForTest.equals;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BadPojoEqualsDifferentObjectSameType {
    private byte byteField;
    private short shortType;
    private byte notIncludedToEqual_byteField;
    private short notIncludedToEqual_shortType;

    @Override
    public String toString() {
        return byteField + " " +
               shortType + " " +
               notIncludedToEqual_byteField + " " +
               notIncludedToEqual_shortType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final BadPojoEqualsDifferentObjectSameType that = (BadPojoEqualsDifferentObjectSameType) o;

        return new EqualsBuilder().append(byteField, that.byteField)
                                  .append(shortType, that.shortType)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(byteField)
                                    .append(shortType)
                                    .toHashCode();
    }
}
