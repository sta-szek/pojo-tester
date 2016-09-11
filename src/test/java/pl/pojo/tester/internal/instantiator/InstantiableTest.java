package pl.pojo.tester.internal.instantiator;

import java.io.Serializable;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.instantiator.Constructor_Field;
import test.instantiator.Constructor_Stream;
import test.instantiator.Constructor_Thread;
import test.instantiator.enums.EmptyEnum;
import test.instantiator.statics.ClassContainingStaticClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class InstantiableTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Instantiator_For_Class() throws NoSuchFieldException {
        return Stream.of(new ClassInstantiator(Serializable.class, ProxyInstantiator.class),
                         new ClassInstantiator(Override.class, ProxyInstantiator.class),
                         new ClassInstantiator(Instantiable.class, ProxyInstantiator.class),
                         new ClassInstantiator(Runnable.class, ProxyInstantiator.class),
                         new ClassInstantiator(EmptyEnum.class, EnumInstantiator.class),
                         new ClassInstantiator(Constructor_Field.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(Constructor_Stream.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(Constructor_Thread.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(Boolean[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Byte[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Character[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Double[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Float[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Integer[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Long[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Short[].class, ArrayInstantiator.class),
                         new ClassInstantiator(boolean[].class, ArrayInstantiator.class),
                         new ClassInstantiator(byte[].class, ArrayInstantiator.class),
                         new ClassInstantiator(char[].class, ArrayInstantiator.class),
                         new ClassInstantiator(double[].class, ArrayInstantiator.class),
                         new ClassInstantiator(float[].class, ArrayInstantiator.class),
                         new ClassInstantiator(int[].class, ArrayInstantiator.class),
                         new ClassInstantiator(long[].class, ArrayInstantiator.class),
                         new ClassInstantiator(short[].class, ArrayInstantiator.class),
                         new ClassInstantiator(Boolean.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Byte.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Character.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Double.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Float.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Integer.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Long.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(Short.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(boolean.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(byte.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(char.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(double.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(float.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(int.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(long.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(short.class, PrimitiveInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PublicConstructor.class,
                                               DefaultConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PackageConstructor.class,
                                               BestConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_ProtectedConstructor.class,
                                               BestConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PrivateConstructor.class,
                                               BestConstructorInstantiator.class))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.clazz.getName()),
                                               Should_Return_Expected_Instantiator_For_Class(value)));
    }

    public Executable Should_Return_Expected_Instantiator_For_Class(final ClassInstantiator testCase) {
        return () -> {
            // when
            final Object result = Instantiable.forClass(testCase.clazz);

            // then
            assertThat(result).isInstanceOf(testCase.instantiator);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Instantiator_For_Class_By_Qualified_Class_Name() throws NoSuchFieldException {
        return Stream.of(new ClassNameInstantiator("org.pojo.tester.instantiator.Instantiable", ProxyInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.enums.EmptyEnum", EnumInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.Constructor_Field", BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.Constructor_Stream", BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.Constructor_Thread", BestConstructorInstantiator.class),
                         new ClassNameInstantiator("java.lang.Boolean", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Byte", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Character", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Double", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Float", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Integer", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Long", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("java.lang.Short", PrimitiveInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                                                   DefaultConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PublicConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PackageConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PrivateConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PublicConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PackageConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                                                   BestConstructorInstantiator.class),
                         new ClassNameInstantiator("test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PrivateConstructor",
                                                   BestConstructorInstantiator.class)
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value.className),
                                               Should_Return_Expected_Instantiator_For_Class_By_Qualified_Class_Name(value)));
    }

    public Executable Should_Return_Expected_Instantiator_For_Class_By_Qualified_Class_Name(final ClassNameInstantiator testCase) {
        return () -> {
            // when
            final Object result = Instantiable.forClass(testCase.className);

            // then
            assertThat(result).isInstanceOf(testCase.instantiator);
        };
    }

    @Test
    public void Should_Throw_Exception_If_Class_Does_Not_Exist() {
        // given

        // when
        final Throwable result = catchThrowable(() -> Instantiable.forClass("lopopopo.ale.tlucze"));

        // then
        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }

    @AllArgsConstructor
    private class ClassInstantiator {
        private Class<?> clazz;
        private Class<?> instantiator;
    }

    @AllArgsConstructor
    private class ClassNameInstantiator {
        private String className;
        private Class<?> instantiator;
    }
}
