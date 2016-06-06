package org.pojo.tester.instantiator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.instantiator.Constructor_Field;
import test.instantiator.Constructor_Stream;
import test.instantiator.Constructor_Thread;
import test.instantiator.enums.EmptyEnum;
import test.instantiator.statics.ClassContainingNestedStaticClasses;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class InstantiableTest {

    @Test
    @Parameters(method = "classesAndInstantiators")
    public void Should_Return_Expected_Instantiator(final Class<?> classToInstantiate, final Class<?> expectedInstantiatorClass) {
        // given

        // when
        final Object result = Instantiable.forClass(classToInstantiate);

        // then
        assertThat(result).isInstanceOf(expectedInstantiatorClass);
    }

    private Object[][] classesAndInstantiators() {
        return new Object[][]{
                {Instantiable.class, ProxyInstantiator.class},
                {EmptyEnum.class, EnumInstantiator.class},
                {Constructor_Field.class, BestConstructorInstantiator.class},
                {Constructor_Stream.class, BestConstructorInstantiator.class},
                {Constructor_Thread.class, BestConstructorInstantiator.class},
                {Boolean[].class, ArrayInstantiator.class},
                {Byte[].class, ArrayInstantiator.class},
                {Character[].class, ArrayInstantiator.class},
                {Double[].class, ArrayInstantiator.class},
                {Float[].class, ArrayInstantiator.class},
                {Integer[].class, ArrayInstantiator.class},
                {Long[].class, ArrayInstantiator.class},
                {Short[].class, ArrayInstantiator.class},
                {boolean[].class, ArrayInstantiator.class},
                {byte[].class, ArrayInstantiator.class},
                {char[].class, ArrayInstantiator.class},
                {double[].class, ArrayInstantiator.class},
                {float[].class, ArrayInstantiator.class},
                {int[].class, ArrayInstantiator.class},
                {long[].class, ArrayInstantiator.class},
                {short[].class, ArrayInstantiator.class},
                {Boolean.class, PrimitiveInstantiator.class},
                {Byte.class, PrimitiveInstantiator.class},
                {Character.class, PrimitiveInstantiator.class},
                {Double.class, PrimitiveInstantiator.class},
                {Float.class, PrimitiveInstantiator.class},
                {Integer.class, PrimitiveInstantiator.class},
                {Long.class, PrimitiveInstantiator.class},
                {Short.class, PrimitiveInstantiator.class},
                {boolean.class, PrimitiveInstantiator.class},
                {byte.class, PrimitiveInstantiator.class},
                {char.class, PrimitiveInstantiator.class},
                {double.class, PrimitiveInstantiator.class},
                {float.class, PrimitiveInstantiator.class},
                {int.class, PrimitiveInstantiator.class},
                {long.class, PrimitiveInstantiator.class},
                {short.class, PrimitiveInstantiator.class},
                {ClassContainingNestedStaticClasses.NestedStaticClass_PublicConstructor.class, DefaultConstructorInstantiator.class},
                {ClassContainingNestedStaticClasses.NestedStaticClass_PackageConstructor.class, BestConstructorInstantiator.class},
                {ClassContainingNestedStaticClasses.NestedStaticClass_ProtectedConstructor.class, BestConstructorInstantiator.class},
                {ClassContainingNestedStaticClasses.NestedStaticClass_PrivateConstructor.class, BestConstructorInstantiator.class},
                };
    }
}
