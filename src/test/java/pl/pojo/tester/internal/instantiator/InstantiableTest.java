package pl.pojo.tester.internal.instantiator;

import classesForTest.instantiator.Constructor_Field;
import classesForTest.instantiator.Constructor_Stream;
import classesForTest.instantiator.Constructor_Thread;
import classesForTest.instantiator.enums.EmptyEnum;
import classesForTest.instantiator.statics.ClassContainingStaticClasses;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ConstructorParameters;

import static classesForTest.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class InstantiableTest {
    private static final Map<Class<?>, ConstructorParameters> CLASS_AND_CONSTRUCTOR_PARAMETERS = new HashMap<>();

    @BeforeAll
    private static void beforeAll() {
        CLASS_AND_CONSTRUCTOR_PARAMETERS.put(UserDefinedClass.class, null);
    }

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
                         new ClassInstantiator(String.class, StringClassInstantiator.class),
                         new ClassInstantiator(UserDefinedClass.class, UserDefinedConstructorInstantiator.class),
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
            final Object result = Instantiable.forClass(testCase.clazz, CLASS_AND_CONSTRUCTOR_PARAMETERS);

            // then
            assertThat(result).isInstanceOf(testCase.instantiator);
        };
    }

    @AllArgsConstructor
    private class ClassInstantiator {
        private Class<?> clazz;
        private Class<?> instantiator;

    }

    private class UserDefinedClass {

    }
}
