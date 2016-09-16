package pl.pojo.tester.internal.instantiator;

import classesForTest.instantiator.Constructor_Stream;
import classesForTest.instantiator.Constructor_Thread;
import classesForTest.instantiator.NoDefaultConstructor;
import classesForTest.instantiator.No_Args_Constructor_Throws_NPE;
import classesForTest.instantiator.One_Arg_Constructor_Throws_NPE;
import classesForTest.instantiator.PackageConstructor;
import classesForTest.instantiator.PrivateConstructor;
import classesForTest.instantiator.ProtectedConstructor;
import classesForTest.instantiator.arrays.*;
import classesForTest.instantiator.statics.ClassContainingStaticClasses;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

import static classesForTest.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class BestConstructorInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Non_Public_Classes() {
        return Stream.of("classesForTest.unpublicClasses.UnpublicClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PrivateStaticFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PrivateStaticFinalNestedClass$PrivateStaticFinalNestedClass2",
                         "classesForTest.unpublicClasses.UnpublicClass$ProtectedStaticFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PackageStaticFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PublicStaticFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PrivateStaticNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$ProtectedStaticNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PackageStaticNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PublicStaticNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PrivateFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$ProtectedFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PackageFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PublicFinalNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PrivateNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$ProtectedNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PackageNestedClass",
                         "classesForTest.unpublicClasses.UnpublicClass$PublicNestedClass")
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Instantiate_Non_Public_Classes(value)));
    }

    public Executable Should_Instantiate_Non_Public_Classes(final String className) {
        return () -> {
            // given
            final Class<?> classUnderTest = Class.forName(className);
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classUnderTest);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classUnderTest);
        };
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
                         MethodUtils.class,
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
