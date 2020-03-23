package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserObjectInstantiatorTest {

    @Test
    public void should_instantiate() {
        // Given
        UserObjectInstantiator instantiator = new UserObjectInstantiator(String.class, new HashSetValuedHashMap<>()) {
            @Override
            public Optional<Object> tryToInstantiate() {
                return Optional.of("a String instance");
            }

            @Override
            public boolean canInstantiate() {
                return true;
            }
        };

        // When
        Object object = instantiator.instantiate();

        // Then
        assertThat(object).isEqualTo("a String instance");
    }

    @Test
    public void should_not_instantiate_and_throw_exception() {
        // Given
        UserObjectInstantiator instantiator = new UserObjectInstantiator(String.class, new HashSetValuedHashMap<>()) {
            @Override
            public Optional<Object> tryToInstantiate() {
                return empty();
            }

            @Override
            public boolean canInstantiate() {
                return true;
            }
        };

        // When
        Executable executable = instantiator::instantiate;

        // Then
        assertThrows(ObjectInstantiationException.class, executable);
    }

}