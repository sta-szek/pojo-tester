package pl.pojo.tester.internal.utils;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class ClassLoaderTest {

    @TestFactory
    Stream<DynamicTest> Should_Load_Expected_Class_By_Qualified_Class_Name() {
        return Stream.of("pl.pojo.tester.internal.instantiator.Instantiable",
                         "classesForTest.EmptyEnum",
                         "classesForTest.Constructor_Field",
                         "classesForTest.Constructor_Stream",
                         "classesForTest.Constructor_Thread",
                         "java.lang.Boolean",
                         "java.lang.Byte",
                         "java.lang.Character",
                         "java.lang.Double",
                         "java.lang.Float",
                         "java.lang.Integer",
                         "java.lang.Long",
                         "java.lang.Short",
                         "classesForTest.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                         "classesForTest.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                         "classesForTest.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                         "classesForTest.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Package_PublicConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Package_PackageConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Package_PrivateConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Private_PublicConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Private_PackageConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                         "classesForTest.ClassContainingUnpublicClasses$Private_PrivateConstructor",
                         "classesForTest.UnpublicClass$PublicStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PublicStaticNestedClass",
                         "classesForTest.UnpublicClass",
                         "classesForTest.UnpublicClass$PrivateStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateStaticFinalNestedClass$PrivateStaticFinalNestedClass2",
                         "classesForTest.UnpublicClass$ProtectedStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PackageStaticFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateStaticNestedClass",
                         "classesForTest.UnpublicClass$ProtectedStaticNestedClass",
                         "classesForTest.UnpublicClass$PackageStaticNestedClass",
                         "classesForTest.UnpublicClass$PrivateFinalNestedClass",
                         "classesForTest.UnpublicClass$ProtectedFinalNestedClass",
                         "classesForTest.UnpublicClass$PackageFinalNestedClass",
                         "classesForTest.UnpublicClass$PublicFinalNestedClass",
                         "classesForTest.UnpublicClass$PrivateNestedClass",
                         "classesForTest.UnpublicClass$ProtectedNestedClass",
                         "classesForTest.UnpublicClass$PackageNestedClass",
                         "classesForTest.UnpublicClass$PublicNestedClass",
                         "java.lang.String"
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Load_Expected_Class_By_Qualified_Class_Name(value)));
    }

    private Executable Should_Load_Expected_Class_By_Qualified_Class_Name(final String qualifiedClassName) {
        return () -> {
            // when
            final Class<?> result = ClassLoader.loadClass(qualifiedClassName);

            // then
            assertThat(result.getName()).isEqualTo(qualifiedClassName);
        };
    }

    @Test
    void Should_Throw_Exception_If_Class_Does_Not_Exist() {
        // given

        // when
        final Throwable result = catchThrowable(() -> ClassLoader.loadClass("lopopopo.ale.tlucze"));

        // then
        assertThat(result).isInstanceOf(ClassLoadingException.class);
    }

}
