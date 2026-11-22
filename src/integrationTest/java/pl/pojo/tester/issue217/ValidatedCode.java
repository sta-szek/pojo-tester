package pl.pojo.tester.issue217;

import java.util.Objects;

public final class ValidatedCode {

    private final String value;

    public ValidatedCode(final String value) {
        if (!"accepted".equals(value) && !"hello".equals(value)) {
            throw new IllegalArgumentException("Argument must be the string 'accepted' or 'hello'");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(value, ((ValidatedCode) other).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ValidatedCode{value=" + value + '}';
    }
}
