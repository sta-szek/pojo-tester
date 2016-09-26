package pl.pojo.tester.internal.instantiator;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.fields.collections.collection.Collections;
import classesForTest.fields.collections.map.Maps;
import classesForTest.instantiator.arrays.ObjectContainingArray;
import classesForTest.instantiator.arrays.ObjectContainingIterable;
import classesForTest.instantiator.arrays.ObjectContainingIterator;
import classesForTest.instantiator.arrays.ObjectContainingStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class ObjectGeneratorTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = DefaultFieldValueChanger.INSTANCE;
    private final Map<Class<?>, ConstructorParameters> constructorParameters = new HashMap<>();

    @Test
    public void Should_Generate_Different_Objects_For_Class_Containing_Boolean_Type() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(ClassWithBooleanField.class);

        // when
        final List<Object> result = objectGenerator.generateDifferentObjects(classAndFieldPredicatePair);

        // then
        assertThat(result).hasSize(2)
                          .doesNotHaveDuplicates();
    }

    @Test
    public void Should_Create_Any_Instance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
        final Class<GoodPojo_Equals_HashCode_ToString> expectedClass = GoodPojo_Equals_HashCode_ToString.class;

        // when
        final Object result = objectGenerator.createNewInstance(expectedClass);

        // then
        assertThat(result).isInstanceOf(expectedClass);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Create_Same_Instance() {
        return Stream.of(new GoodPojo_Equals_HashCode_ToString(),
                         new ObjectContainingArray(),
                         new Collections(),
                         new Maps())
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Create_Same_Instance(value)));
    }

    public Executable Should_Create_Same_Instance(final Object objectToCreateSameInstance) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);

            // when
            final Object result = objectGenerator.generateSameInstance(objectToCreateSameInstance);

            // then
            assertThat(result).isEqualToComparingFieldByField(objectToCreateSameInstance);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Generate_Different_Objects() {
        return Stream.of(new DifferentObjectTestCase(A.class, 4),
                         new DifferentObjectTestCase(B.class, 8),
                         new DifferentObjectTestCase(C.class, 16),
                         new DifferentObjectTestCase(ObjectContainingArray.class, 2),
                         new DifferentObjectTestCase(ObjectContainingIterable.class, 2),
                         new DifferentObjectTestCase(ObjectContainingIterator.class, 2),
                         new DifferentObjectTestCase(ObjectContainingStream.class, 2),
                         new DifferentObjectTestCase(Collections.class, 4096),
                         new DifferentObjectTestCase(Maps.class, 64),
                         new DifferentObjectTestCase(GoodPojo_Equals_HashCode_ToString.class, 1024))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Generate_Different_Objects(value)));
    }

    public Executable Should_Generate_Different_Objects(final DifferentObjectTestCase testCase) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
            final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(testCase.clazz);

            // when
            final List<Object> result = objectGenerator.generateDifferentObjects(classAndFieldPredicatePair);

            // then
            assertThat(result).hasSize(testCase.expectedSize)
                              .doesNotHaveDuplicates();
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Generate_Different_Objects_Recursively() throws IllegalAccessException {
        final RecursivelyDifferentObjectTestCase case1 = new RecursivelyDifferentObjectTestCase(18,
                                                                                                pair(D.class),
                                                                                                new ClassAndFieldPredicatePair[]{pair(E.class), pair(F.class)});

        final RecursivelyDifferentObjectTestCase case2 = new RecursivelyDifferentObjectTestCase(6,
                                                                                                pair(G.class),
                                                                                                new ClassAndFieldPredicatePair[]{pair(F.class)});

        final RecursivelyDifferentObjectTestCase case3 = new RecursivelyDifferentObjectTestCase(945,
                                                                                                pair(H.class),
                                                                                                new ClassAndFieldPredicatePair[]{pair(A.class),
                                                                                                                                 pair(B.class),
                                                                                                                                 pair(F.class),
                                                                                                                                 pair(G.class)});

        return Stream.of(case1, case2, case3)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Generate_Different_Objects_Recursively(value)));
    }

    public Executable Should_Generate_Different_Objects_Recursively(final RecursivelyDifferentObjectTestCase testCase) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);

            // when
            final List<Object> result = objectGenerator.generateDifferentObjects(testCase.baseClass, testCase.otherClasses);

            // then
            assertThat(result).hasSize(testCase.expectedSize)
                              .doesNotHaveDuplicates();
        };
    }

    @Test
    public void Should_Not_Fall_In_Endless_Loop() throws IllegalAccessException {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
        final ClassAndFieldPredicatePair iClass = new ClassAndFieldPredicatePair(R.class);
        final int expectedSize = 2;

        // when
        final List<Object> result = objectGenerator.generateDifferentObjects(iClass, iClass);

        // then
        assertThat(result).hasSize(expectedSize)
                          .doesNotHaveDuplicates();
    }

    private ClassAndFieldPredicatePair pair(final Class<?> clazz) {
        return new ClassAndFieldPredicatePair(clazz);
    }

    @Data
    class A {
        int a;
        int b;
    }

    @Data
    class B {
        int a;
        int b;
        int c;
    }

    @Data
    class C {
        int a;
        int b;
        int c;
        int d;
    }

    @Data
    class D {
        int a;
        E e;
        F f;
    }

    @Data
    class E {
        int b;
    }

    @Data
    class F {
        int d;
    }

    @Data
    class G {
        int d;
        F f;
    }

    @Data
    class H {
        A a;
        B b;
        F f;
        G g;
    }

    @Data
    class R {
        R r;
    }

    @Data
    class ClassWithBooleanField {
        private boolean booleanField;
    }

    @Data
    @AllArgsConstructor
    class DifferentObjectTestCase {
        private Class<?> clazz;
        private int expectedSize;
    }

    @Data
    @AllArgsConstructor
    class RecursivelyDifferentObjectTestCase {
        private int expectedSize;
        private ClassAndFieldPredicatePair baseClass;
        private ClassAndFieldPredicatePair[] otherClasses;
    }
}
