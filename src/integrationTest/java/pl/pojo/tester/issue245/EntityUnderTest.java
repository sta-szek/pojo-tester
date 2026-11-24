package pl.pojo.tester.issue245;

import java.util.Objects;

public class EntityUnderTest {

    private ChildEntity faultInjectionParameters = new ChildEntity();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EntityUnderTest that = (EntityUnderTest) o;
        return Objects.equals(faultInjectionParameters, that.faultInjectionParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(faultInjectionParameters);
    }
}
