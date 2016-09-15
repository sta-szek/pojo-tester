package classesForTest.equals;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BadPojoEqualsWithEqualObject {
    public byte byteField;
    public short shortType;
    public int intType;
    public long longType;
    public double doubleType;
    public boolean booleanType;
    public char charType;
    public float floatType;

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(final Object o) {
        return !trueEqual(o);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(byteField)
                .append(shortType)
                .append(intType)
                .append(longType)
                .append(doubleType)
                .append(booleanType)
                .append(charType)
                .append(floatType)
                .toHashCode();
    }

    private boolean trueEqual(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final BadPojoEqualsWithEqualObject that = (BadPojoEqualsWithEqualObject) o;

        return new EqualsBuilder()
                .append(byteField, that.byteField)
                .append(shortType, that.shortType)
                .append(intType, that.intType)
                .append(longType, that.longType)
                .append(doubleType, that.doubleType)
                .append(booleanType, that.booleanType)
                .append(charType, that.charType)
                .append(floatType, that.floatType)
                .isEquals();
    }
}
