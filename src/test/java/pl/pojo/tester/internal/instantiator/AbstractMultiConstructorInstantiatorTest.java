package pl.pojo.tester.internal.instantiator;

import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class AbstractMultiConstructorInstantiatorTest {

    @Test
    void Should_Create_Object_Using_User_Parameters() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = A.class;
        constructorParameters.put(clazz, new ConstructorParameters(new Object[]{12345}, new Class[]{int.class}));
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);
        final A expectedResult = new A(12345);

        // when
        final Object result = instantiator.instantiateUsingUserParameters();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void Should_Return_Null_If_Parameters_For_This_Class_Are_Empty() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = A.class;
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);

        // when
        final Object result = instantiator.instantiateUsingUserParameters();

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Throw_Exception_If_Constructor_Throws_Exception() {
        // given
        final ArrayListValuedHashMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
        final Class<?> clazz = B.class;
        final AbstractMultiConstructorInstantiator instantiator = new MockMultiConstructorInstantiator(clazz,
                                                                                                       constructorParameters);
        final Throwable expectedResult = new ObjectInstantiationException(B.class, "msg", null);

        // when
        final Throwable result = catchThrowable(() -> instantiator.createFindingBestConstructor());

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    class MockMultiConstructorInstantiator extends AbstractMultiConstructorInstantiator {
        MockMultiConstructorInstantiator(final Class<?> clazz, final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
            super(clazz, constructorParameters);
        }

        @Override
        public Object instantiate() {
            return null;
        }

        @Override
        protected Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, final Object[] parameters) throws ObjectInstantiationException {
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