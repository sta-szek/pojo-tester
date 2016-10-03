package classesForTest;

import java.util.UUID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Person {

    private final UUID id;
    private final String name;

    private Person(final String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Person person = (Person) obj;

        return new EqualsBuilder().append(id, person.id)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id)
                                    .toHashCode();
    }

    public static class PersonBuilder {

        private String name;

        public PersonBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public Person build() {
            return new Person(name);
        }
    }
}
