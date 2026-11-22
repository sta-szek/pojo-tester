package pl.pojo.tester.issue217;

import java.util.Objects;

public final class Built {

    private final String name;

    private Built(final String name) {
        if (!"n".equals(name)) {
            throw new IllegalArgumentException("name");
        }
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(name, ((Built) other).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Built{name=" + name + '}';
    }

    public static final class Builder {

        private String name;

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Built build() {
            return new Built(name);
        }
    }
}
