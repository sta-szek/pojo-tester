package pl.pojo.tester.internal.instantiator;

import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class AbstractMultiConstructorInstantiatorTest {

    @Test
    void Should_Throw_Exception_If_Constructor_Throws_Exception() {
        // given
        final Class<?> clazz = B.class;
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz);
        final Throwable expectedResult = new ObjectInstantiationException(B.class, "msg", null);

        // when
        final Throwable result = catchThrowable(instantiator::createFindingBestConstructor);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    static class MockMultiConstructorInstantiator extends AbstractMultiConstructorInstantiator {
        MockMultiConstructorInstantiator(final Class<?> clazz) {
            super(clazz, new LinkedList<>());
        }

        @Override
        public Object instantiate() {
            return null;
        }

        @Override
        public boolean canInstantiate() {
            return true;
        }

        @Override
        protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes,
                                                         final Object[] parameters) throws ObjectInstantiationException {
            try {
                final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
                declaredConstructor.setAccessible(true);
                return declaredConstructor.newInstance(parameters);
            } catch (final Exception e) {
                throw new ObjectInstantiationException(null, null);
            }
        }

        @Override
        protected Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
            return null;
        }

        @Override
        protected ObjectInstantiationException createObjectInstantiationException() {
            return new ObjectInstantiationException(B.class, "msg", null);
        }
    }

    @Data
    private class A {
        private final int b;
    }

    private class B {

        B(final int a) {
            throw new RuntimeException("eee");
        }
    }
}