package pl.pojo.tester.internal.tester;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Set;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class SetFieldHashCodeTest {

    @Test
    void Should_Tell_A_Null_Set_From_An_Empty_Set() {
        assertPojoMethodsFor(User.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    static class User {
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
}
