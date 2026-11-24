package pl.pojo.tester.issue245;

import java.util.Objects;

class ChildEntity {

    private String value;

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(value, ((ChildEntity) other).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
