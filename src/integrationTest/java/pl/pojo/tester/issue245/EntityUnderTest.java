package pl.pojo.tester.issue245;

import java.util.Objects;

class EntityUnderTest {

    private ChildEntity faultInjectionParameters = new ChildEntity();

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(faultInjectionParameters, ((EntityUnderTest) other).faultInjectionParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faultInjectionParameters);
    }
}
