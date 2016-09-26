package pl.pojo.tester.internal.instantiator;

import classesForTest.ClassContainingStaticClasses;
import classesForTest.Constructor_Stream;
import classesForTest.Constructor_Thread;
import classesForTest.PackageConstructor;
import classesForTest.PrivateConstructor;
import classesForTest.ProtectedConstructor;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class BestConstructorInstantiatorTest {

    private final Map<Class<?>, ConstructorParameters> constructorParameters = new HashMap<>();

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Non_Public_Classes() {
        return Stream.of("classesForTest.UnpublicClass",
                         "classesForTest.UnpublicClass$PrivateStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateStaticFinalNestedClass$PrivateStaticFinalNestedClass2",
                         "classesForTest.UnpublicClass$ProtectedStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PackageStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PublicStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateStaticNestedClass",
                         "classesForTest.UnpublicClass$ProtectedStaticNestedClass",
                         "classesForTest.UnpublicClass$PackageStaticNestedClass",
                         "classesForTest.UnpublicClass$PublicStaticNestedClass",
                         "classesForTest.UnpublicClass$PrivateFinalNestedClass",
                         "classesForTest.UnpublicClass$ProtectedFinalNestedClass",
                         "classesForTest.UnpublicClass$PackageFinalNestedClass",
                         "classesForTest.UnpublicClass$PublicFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateNestedClass",
                         "classesForTest.UnpublicClass$ProtectedNestedClass",
                         "classesForTest.UnpublicClass$PackageNestedClass",
                         "classesForTest.UnpublicClass$PublicNestedClass")
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Instantiate_Non_Public_Classes(value)));
    }

    public Executable Should_Instantiate_Non_Public_Classes(final String className) {
        return () -> {
            // given
            final Class<?> classUnderTest = Class.forName(className);
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classUnderTest, constructorParameters);

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
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate, constructorParameters);

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
            final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate, constructorParameters);

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
        final BestConstructorInstantiator instantiator = new BestConstructorInstantiator(classToInstantiate, constructorParameters);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    private class One_Arg_Constructor_Throws_NPE {
        public One_Arg_Constructor_Throws_NPE(final Object o) {
            throw new NullPointerException("test");
        }
    }

    private class NoDefaultConstructor {

        private int a;
        private int b;
        private int c;
        private Object object;

        public NoDefaultConstructor(final int a) {
            this.a = a;
        }

        public NoDefaultConstructor(final Object object) {
            this.object = object;
        }
    }

    private class No_Args_Constructor_Throws_NPE {

        public No_Args_Constructor_Throws_NPE() {
            throw new NullPointerException("test");
        }
    }

    private class Constructor_Array_Boolean {

        private final Boolean[] array;

        public Constructor_Array_Boolean(final Boolean[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Boolean_Primitive {

        private final boolean[] array;

        public Constructor_Array_Boolean_Primitive(final boolean[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Byte {

        private final Byte[] array;

        public Constructor_Array_Byte(final Byte[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Byte_Primitive {

        private final byte[] array;

        public Constructor_Array_Byte_Primitive(final byte[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Char {

        private final Character[] array;

        public Constructor_Array_Char(final Character[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Char_Primitive {

        private final char[] array;

        public Constructor_Array_Char_Primitive(final char[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Double {

        private final Double[] array;

        public Constructor_Array_Double(final Double[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Double_Primitive {

        private final double[] array;

        public Constructor_Array_Double_Primitive(final double[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Float {

        private final Float[] array;

        public Constructor_Array_Float(final Float[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Float_Primitive {

        private final float[] array;

        public Constructor_Array_Float_Primitive(final float[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Int {

        private final Integer[] array;

        public Constructor_Array_Int(final Integer[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Int_Primitive {

        private final int[] array;

        public Constructor_Array_Int_Primitive(final int[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Long {

        private final Long[] array;

        public Constructor_Array_Long(final Long[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Long_Primitive {

        private final long[] array;

        public Constructor_Array_Long_Primitive(final long[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Short {

        private final Short[] array;

        public Constructor_Array_Short(final Short[] array) {
            this.array = array;
        }
    }

    private class Constructor_Array_Short_Primitive {

        private final short[] array;

        public Constructor_Array_Short_Primitive(final short[] array) {
            this.array = array;
        }
    }
}
