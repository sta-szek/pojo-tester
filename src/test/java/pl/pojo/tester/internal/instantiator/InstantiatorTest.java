package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class InstantiatorTest {

    @AfterEach
    void detachUserInstantiators() {
        Instantiator.INSTANCE
                .detach(CustomInstantiator.class)
                .detach(CustomInstantiator2.class)
                .detach(StringInstantiator.class);
    }

    @Test
    void should_attach_instantiators() {
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

    @Test
    void should_select_attached_instantiator() {
        Instantiator.INSTANCE.attach(StringInstantiator.class);

        final AbstractObjectInstantiator instantiator = Instantiable.forClass(String.class, new HashSetValuedHashMap<>());

        assertThat(instantiator).isInstanceOf(StringInstantiator.class);
        assertThat(instantiator.instantiate()).isEqualTo("from-user");
    }

    @Test
    void should_not_detach_built_in_instantiators() {
        final int before = Instantiator.INSTANTIATORS.size();

        Instantiator.INSTANCE.detach(JavaTypeInstantiator.class);

        assertThat(Instantiator.INSTANTIATORS).hasSize(before); // NOSONAR java:S3415 actual is the collection
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

    private static class StringInstantiator extends UserObjectInstantiator {

        StringInstantiator(final Class<?> clazz,
                           final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
            super(clazz, constructorParameters);
        }

        @Override
        public Optional<Object> tryToInstantiate() {
            return Optional.of("from-user");
        }

        @Override
        public boolean canInstantiate() {
            return clazz == String.class;
        }
    }

}
