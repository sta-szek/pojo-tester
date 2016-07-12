package org.pojo.tester.instantiator;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.pojo.tester.FieldPredicate;
import org.pojo.tester.field.FieldUtils;
import test.instantiator.Constructor_Stream;
import test.instantiator.Constructor_Thread;
import test.instantiator.NoDefaultConstructor;
import test.instantiator.No_Args_Constructor_Throws_NPE;
import test.instantiator.One_Arg_Constructor_Throws_NPE;
import test.instantiator.PackageConstructor;
import test.instantiator.PrivateConstructor;
import test.instantiator.ProtectedConstructor;
import test.instantiator.arrays.*;
import test.instantiator.statics.ClassContainingStaticClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;


public class BestConstructorInstantiatorTest {

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

    public Executable Should_Create_Object_Using_Best_Constructor(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Cannot_Instantiate_Class() {
        return Stream.of(One_Arg_Constructor_Throws_NPE.class,
                         No_Args_Constructor_Throws_NPE.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Throw_Exception_When_Cannot_Instantiate_Class(value)));
    }

    public Executable Should_Throw_Exception_When_Cannot_Instantiate_Class(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate);

            // when
            final Throwable result = catchThrowable(instantiator::instantiate);

            // then
            assertThat(result).isInstanceOf(ObjectInstantiationException.class);
        };
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

}
