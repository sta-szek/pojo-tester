package pl.pojo.tester.internal.instantiator;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class ClassLoaderTest {

    @TestFactory
    public Stream<DynamicTest> Should_Load_Expected_Class_By_Qualified_Class_Name() throws NoSuchFieldException {
        return Stream.of("pl.pojo.tester.internal.instantiator.Instantiable",
                         "classesForTest.instantiator.enums.EmptyEnum",
                         "classesForTest.instantiator.Constructor_Field",
                         "classesForTest.instantiator.Constructor_Stream",
                         "classesForTest.instantiator.Constructor_Thread",
                         "java.lang.Boolean",
                         "java.lang.Byte",
                         "java.lang.Character",
                         "java.lang.Double",
                         "java.lang.Float",
                         "java.lang.Integer",
                         "java.lang.Long",
                         "java.lang.Short",
                         "classesForTest.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PublicConstructor",
                         "classesForTest.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PackageConstructor",
                         "classesForTest.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_ProtectedConstructor",
                         "classesForTest.instantiator.statics.ClassContainingStaticClasses$NestedStaticClass_PrivateConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PublicConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PackageConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Package_ProtectedConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Package_PrivateConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PublicConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PackageConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_ProtectedConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Protected_PrivateConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PublicConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PackageConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Private_ProtectedConstructor",
                         "classesForTest.instantiator.unpublic.ClassContainingUnpublicClasses$Private_PrivateConstructor",
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

    public Executable Should_Load_Expected_Class_By_Qualified_Class_Name(final String qualifiedClassName) {
        return () -> {
            // when
            final Class<?> result = ClassLoader.loadClass(qualifiedClassName);

            // then
            assertThat(result.getName()).isEqualTo(qualifiedClassName);
        };
    }

    @Test
    public void Should_Throw_Exception_If_Class_Does_Not_Exist() {
        // given

        // when
        final Throwable result = catchThrowable(() -> ClassLoader.loadClass("lopopopo.ale.tlucze"));

        // then
        assertThat(result).isInstanceOf(ClassLoadingException.class);
    }

}
