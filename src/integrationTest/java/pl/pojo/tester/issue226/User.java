package pl.pojo.tester.issue226;

import java.util.Objects;
import java.util.Set;

class User {

    private String firstName;
    private Set<String> authorities;

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final User user = (User) other;
        return Objects.equals(firstName, user.firstName) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, authorities);
    }
}
