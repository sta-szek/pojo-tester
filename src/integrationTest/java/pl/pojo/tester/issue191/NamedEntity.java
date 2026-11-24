package pl.pojo.tester.issue191;

import java.util.Objects;

class NamedEntity {

    static int bothNull;

    private String name;

    NamedEntity(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final NamedEntity that = (NamedEntity) other;
        if (name == null && that.name == null) {
            bothNull++;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
