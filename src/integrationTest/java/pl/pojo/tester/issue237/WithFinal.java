package pl.pojo.tester.issue237;

import java.util.Objects;

class WithFinal {

    private final String id = "fixed";
    private String name;

    @Override
    public boolean equals(final Object other) {
        requireUntouched();
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final WithFinal that = (WithFinal) other;
        that.requireUntouched();
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        requireUntouched();
        return Objects.hash(name);
    }

    private void requireUntouched() {
        if (!"fixed".equals(id)) {
            throw new IllegalStateException("final field was changed: " + id);
        }
    }
}
