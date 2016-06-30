package org.pojo.tester.instantiator;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.FieldPredicate;
import org.pojo.tester.field.FieldUtils;
import test.instantiator.Constructor_Stream;
import test.instantiator.Constructor_Thread;
import test.instantiator.NoDefaultConstructor;
import test.instantiator.PackageConstructor;
import test.instantiator.PrivateConstructor;
import test.instantiator.ProtectedConstructor;
import test.instantiator.arrays.*;
import test.instantiator.statics.ClassContainingStaticClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class BestConstructorInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Create_Object_Using_Best_Constructor_By_Qualified_Class_Name() {
        return Stream.of("test.instantiator.arrays.Constructor_Array_Boolean",
                         "test.instantiator.arrays.Constructor_Array_Boolean_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Byte",
                         "test.instantiator.arrays.Constructor_Array_Byte_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Char",
                         "test.instantiator.arrays.Constructor_Array_Char_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Double",
                         "test.instantiator.arrays.Constructor_Array_Double_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Float",
                         "test.instantiator.arrays.Constructor_Array_Float_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Int",
                         "test.instantiator.arrays.Constructor_Array_Int_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Long",
                         "test.instantiator.arrays.Constructor_Array_Long_Primitive",
                         "test.instantiator.arrays.Constructor_Array_Short",
                         "test.instantiator.arrays.Constructor_Array_Short_Primitive",
                         "test.instantiator.Constructor_Stream",
                         "test.instantiator.Constructor_Thread",
                         "test.instantiator.NoDefaultConstructor",
                         "test.instantiator.PackageConstructor",
                         "test.instantiator.PrivateConstructor",
                         "test.instantiator.ProtectedConstructor",
                         "test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                         "test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                         "test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                         "test.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PublicConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PackageConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PrivateConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PublicConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PackageConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                         "test.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PrivateConstructor")
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Create_Object_Using_Best_Constructor_By_Qualified_Class_Name(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Create_Object_Using_Best_Constructor() {
        return Stream.of(Constructor_Array_Boolean.class,
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
                         ProtectedConstructor.class,
                         ClassContainingStaticClasses.NestedStaticClass_PublicConstructor.class,
                         ClassContainingStaticClasses.NestedStaticClass_PackageConstructor.class,
                         ClassContainingStaticClasses.NestedStaticClass_ProtectedConstructor.class,
                         ClassContainingStaticClasses.NestedStaticClass_PrivateConstructor.class,
                         FieldUtils.class,
                         FieldPredicate.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Create_Object_Using_Best_Constructor(value)));
    }

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

    private Executable Should_Create_Object_Using_Best_Constructor_By_Qualified_Class_Name(final String qualifiedClassName) {
        return () -> {
            // given
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(qualifiedClassName);
            final String classNameWithoutDollars = qualifiedClassName.replace("$", ".");

            // when
            final Object object = instantiator.instantiate();
            final String result = object.getClass()
                                        .getCanonicalName();

            // then
            assertThat(result).isEqualTo(classNameWithoutDollars);
        };
    }

    private Executable Should_Create_Object_Using_Best_Constructor(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

}
