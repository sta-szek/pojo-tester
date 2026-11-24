package pl.pojo.tester.issue250;

import java.util.Objects;

class ConstantHash {

    private String name;
    private int age;

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final ConstantHash that = (ConstantHash) other;
        return age == that.age && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
