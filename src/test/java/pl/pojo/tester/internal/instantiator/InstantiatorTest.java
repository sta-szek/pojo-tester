package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class InstantiatorTest {

    @Test
    public void should_attach_instantiators() {
        Instantiator.INSTANCE
                .attach(CustomInstantiator.class)
                .attach(CustomInstantiator2.class);

        assertThat(Instantiator.INSTANTIATORS)
                .containsExactly(
                        CustomInstantiator.class,
                        CustomInstantiator2.class,
                        UserDefinedConstructorInstantiator.class,
                        JavaTypeInstantiator.class,
                        CollectionInstantiator.class,
                        DefaultConstructorInstantiator.class,
                        EnumInstantiator.class,
                        ArrayInstantiator.class,
                        ProxyInstantiator.class,
                        BestConstructorInstantiator.class
                );
    }

    private static class CustomInstantiator extends UserObjectInstantiator {

        CustomInstantiator(final Class<?> clazz,
                           final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
            super(clazz, constructorParameters);

        }

        @Override
        public Optional<Object> tryToInstantiate() {
            return empty();
        }

        @Override
        public boolean canInstantiate() {
            return false;
        }
    }

    private static class CustomInstantiator2 extends UserObjectInstantiator {

        CustomInstantiator2(final Class<?> clazz,
                           final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
            super(clazz, constructorParameters);

        }

        @Override
        public Optional<Object> tryToInstantiate() {
            return empty();
        }

        @Override
        public boolean canInstantiate() {
            return false;
        }
    }

}