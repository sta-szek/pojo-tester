package org.pojo.tester.instantiator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
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

    @Test
    public void Should_Generate_Different_Objects_Recursively_1() throws IllegalAccessException {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final ClassAndFieldPredicatePair dClass = new ClassAndFieldPredicatePair(D.class);
        final ClassAndFieldPredicatePair eClass = new ClassAndFieldPredicatePair(E.class);
        final ClassAndFieldPredicatePair fClass = new ClassAndFieldPredicatePair(F.class);
        final int expectedSize = 18;

        // when
        final List<Object> result = objectGenerator.generateDifferentObjects(dClass, eClass, fClass);

        // then
        assertThat(result).hasSize(expectedSize)
                          .doesNotHaveDuplicates();
    }

    @Test
    public void Should_Generate_Different_Objects_Recursively_2() throws IllegalAccessException {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final ClassAndFieldPredicatePair gClass = new ClassAndFieldPredicatePair(G.class);
        final ClassAndFieldPredicatePair fClass = new ClassAndFieldPredicatePair(F.class);
        final int expectedSize = 6;

        // when
        final List<Object> result = objectGenerator.generateDifferentObjects(gClass, fClass);

        // then
        assertThat(result).hasSize(expectedSize)
                          .doesNotHaveDuplicates();
    }

    @Test
    public void Should_Generate_Different_Objects_Recursively_5() throws IllegalAccessException {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
        final ClassAndFieldPredicatePair hClass = new ClassAndFieldPredicatePair(H.class);
        final ClassAndFieldPredicatePair aClass = new ClassAndFieldPredicatePair(A.class);
        final ClassAndFieldPredicatePair bClass = new ClassAndFieldPredicatePair(B.class);
        final ClassAndFieldPredicatePair fClass = new ClassAndFieldPredicatePair(F.class);
        final ClassAndFieldPredicatePair gClass = new ClassAndFieldPredicatePair(G.class);
        final int expectedSize = 945;

        // when
        final List<Object> result = objectGenerator.generateDifferentObjects(hClass,
                                                                             aClass,
                                                                             bClass,
                                                                             fClass,
                                                                             gClass);

        // then
        assertThat(result).hasSize(expectedSize)
                          .doesNotHaveDuplicates();
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
}
