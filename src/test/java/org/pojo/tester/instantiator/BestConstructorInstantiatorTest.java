package org.pojo.tester.instantiator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.instantiator.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class BestConstructorInstantiatorTest {

    @Test
    public void Should_Create_Object_Using_Private_Constructor() {
        // given
        final Class<PrivateConstructor> classToInstantiate = PrivateConstructor.class;
        final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    @Parameters(method = "arrayClassesToInstantiate")
    public void Should_Create_Object_Using_Best_Constructor(final Class<?> classToInstantiate) {
        // given
        final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    private Object[] arrayClassesToInstantiate() {
        return new Object[]{Constructor_Array_Boolean.class,
                            Constructor_Array_Boolean_Primitive.class,
                            Constructor_Array_Byte.class,
                            Constructor_Array_Byte_Primitive.class,
                            Constructor_Array_Char.class,
                            Constructor_Array_Char_Primitive.class,
                            Constructor_Array_Double.class,
                            Constructor_Array_Double_Primitive.class,
                            Constructor_Array_Float.class,
                            Constructor_Array_Float_Primitive.class,
                            Constructor_Array_Int.class,
                            Constructor_Array_Int_Primitive.class,
                            Constructor_Array_Long.class,
                            Constructor_Array_Long_Primitive.class,
                            Constructor_Array_Short.class,
                            Constructor_Array_Short_Primitive.class,
                            Constructor_Stream.class,
                            Constructor_Thread.class,
                            NoDefaultConstructor.class,
                            PackageConstructor.class,
                            PrivateConstructor.class,
                            ProtectedConstructor.class
        };
    }
}
