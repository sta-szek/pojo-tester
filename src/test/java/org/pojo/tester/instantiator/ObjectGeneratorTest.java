package org.pojo.tester.instantiator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.ClassAndFieldPredicatePair;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import test.GoodPojo_Equals_HashCode_ToString;
import test.TestHelper;
import test.fields.collections.collection.Collections;
import test.fields.collections.map.Maps;
import test.instantiator.arrays.ObjectContainingArray;
import test.instantiator.arrays.ObjectContainingIterable;
import test.instantiator.arrays.ObjectContainingIterator;
import test.instantiator.arrays.ObjectContainingStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class ObjectGeneratorTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = DefaultFieldValueChanger.INSTANCE;

    @Test
    public void Should_Create_Any_Instance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
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
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);

            // when
            final Object result = objectGenerator.generateSameInstance(objectToCreateSameInstance);

            // then
            assertThat(result).isEqualToComparingFieldByField(objectToCreateSameInstance);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Create_Different_Instance() {
        return Stream.of(new ObjectContainingArray(),
                         new ObjectContainingIterable(),
                         new ObjectContainingIterator(),
                         new ObjectContainingStream(),
                         new Collections(),
                         new Maps(),
                         new GoodPojo_Equals_HashCode_ToString())
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Create_Different_Instance(value)));
    }

    public Executable Should_Create_Different_Instance(final Object objectToCreateSameInstance) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
            final List<Field> allFields = TestHelper.getAllFieldsExceptDummyJacocoField(objectToCreateSameInstance.getClass());

            // when
            final Object result = objectGenerator.generateInstanceWithDifferentFieldValues(objectToCreateSameInstance, allFields);

            // then
            assertThat(result).isNotEqualTo(objectToCreateSameInstance);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Generate_Different_Objects() {
        return Stream.of(new DifferentObjectTestCase(A.class, 4),
                         new DifferentObjectTestCase(B.class, 8),
                         new DifferentObjectTestCase(C.class, 16))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Generate_Different_Objects(value)));
    }

    public Executable Should_Generate_Different_Objects(final DifferentObjectTestCase testCase) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
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
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);

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
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
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
