package org.pojo.tester.instantiator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.instantiator.Constructor_Field;
import test.instantiator.Constructor_Stream;
import test.instantiator.Constructor_Thread;
import test.instantiator.enums.EmptyEnum;
import test.instantiator.statics.ClassContainingStaticClasses;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class InstantiableTest {

    @Test
    @Parameters(method = "classesAndInstantiators")
    public void Should_Return_Expected_Instantiator_For_Class(final Class<?> classToInstantiate, final Class<?> expectedInstantiatorClass) {
        // given

        // when
        final Object result = Instantiable.forClass(classToInstantiate);

        // then
        assertThat(result).isInstanceOf(expectedInstantiatorClass);
    }

    @Test
    @Parameters(method = "classesNamesToInstantiate")
    public void Should_Return_Expected_Instantiator_For_Class_By_Qualified_Class_Name(final String qualifiedClassName,
                                                                                      final Class<?> expectedInstantiatorClass) {
        // given

        // when
        final Object result = Instantiable.forClass(qualifiedClassName);

        // then
        assertThat(result).isInstanceOf(expectedInstantiatorClass);
    }

    private Object[][] classesNamesToInstantiate() {
        return new Object[][]{
                {"org.pojo.tester.instantiator.Instantiable", ProxyInstantiator.class},
                {"test.instantiator.enums.EmptyEnum", EnumInstantiator.class},
                {"test.instantiator.Constructor_Field", BestConstructorInstantiator.class},
                {"test.instantiator.Constructor_Stream", BestConstructorInstantiator.class},
                {"test.instantiator.Constructor_Thread", BestConstructorInstantiator.class},
                {"java.lang.Boolean", PrimitiveInstantiator.class},
                {"java.lang.Byte", PrimitiveInstantiator.class},
                {"java.lang.Character", PrimitiveInstantiator.class},
                {"java.lang.Double", PrimitiveInstantiator.class},
                {"java.lang.Float", PrimitiveInstantiator.class},
                {"java.lang.Integer", PrimitiveInstantiator.class},
                {"java.lang.Long", PrimitiveInstantiator.class},
                {"java.lang.Short", PrimitiveInstantiator.class},
                {"test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                 DefaultConstructorInstantiator.class},
                {"test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PublicConstructor", BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PackageConstructor", BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PrivateConstructor", BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PublicConstructor", BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PackageConstructor", BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                 BestConstructorInstantiator.class},
                {"test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PrivateConstructor", BestConstructorInstantiator.class},
                };
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
                {ClassContainingStaticClasses.NestedStaticClass_PublicConstructor.class, DefaultConstructorInstantiator.class},
                {ClassContainingStaticClasses.NestedStaticClass_PackageConstructor.class, BestConstructorInstantiator.class},
                {ClassContainingStaticClasses.NestedStaticClass_ProtectedConstructor.class, BestConstructorInstantiator.class},
                {ClassContainingStaticClasses.NestedStaticClass_PrivateConstructor.class, BestConstructorInstantiator.class},
                };
    }
}
