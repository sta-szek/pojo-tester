package pl.pojo.tester.internal.instantiator;

import classesForTest.ObjectContainingArray;
import classesForTest.ObjectContainingIterable;
import classesForTest.ObjectContainingIterator;
import classesForTest.ObjectContainingStream;
import classesForTest.fields.TestEnum1;
import classesForTest.fields.collections.collection.Collections;
import classesForTest.fields.collections.map.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class ObjectGeneratorTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = DefaultFieldValueChanger.INSTANCE;
    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new
            ArrayListValuedHashMap<>();

    @Test
    public void Should_Generate_Different_Objects_For_Class_Containing_Boolean_Type() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(
                ClassWithBooleanField.class);

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
        final Class<?> expectedClass = GoodPojo_Equals_HashCode_ToString.class;

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
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger,
                                                                        constructorParameters);

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
                         new DifferentObjectTestCase(GoodPojo_Equals_HashCode_ToString.class, 1024),
                         new DifferentObjectTestCase(Arrays.class, 65536)
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Generate_Different_Objects(value)));
    }

    public Executable Should_Generate_Different_Objects(final DifferentObjectTestCase testCase) {
        return () -> {
            // given
            final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldValueChanger,
                                                                        constructorParameters);
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
    @AllArgsConstructor
    class Arrays {
        private final boolean[] a = new boolean[]{};
        private final byte[] b = new byte[]{};
        private final char[] c = new char[]{};
        private final double[] d = new double[]{};
        private final float[] e = new float[]{};
        private final int[] f = new int[]{};
        private final long[] g = new long[]{};
        private final short[] h = new short[]{};

        private final Boolean[] i = new Boolean[]{};
        private final Byte[] j = new Byte[]{};
        private final Character[] k = new Character[]{};
        private final Double[] l = new Double[]{};
        private final Float[] m = new Float[]{};
        private final Integer[] n = new Integer[]{};
        private final Long[] o = new Long[]{};
        private final Short[] p = new Short[]{};
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

    private class GoodPojo_Equals_HashCode_ToString {
        public long random;
        public byte byteField;
        public short shortType;
        public int intType;
        public long longType;
        public double doubleType;
        public boolean booleanType;
        public float floatType;
        public char charType;
        public TestEnum1 testEnum1;

        public GoodPojo_Equals_HashCode_ToString() {
            final Random random = new Random();
            this.random = random.nextLong();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("random", random)
                                            .append("byteField", byteField)
                                            .append("shortType", shortType)
                                            .append("intType", intType)
                                            .append("longType", longType)
                                            .append("doubleType", doubleType)
                                            .append("booleanType", booleanType)
                                            .append("floatType", floatType)
                                            .append("charType", charType)
                                            .append("testEnum1", testEnum1)
                                            .toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final GoodPojo_Equals_HashCode_ToString that = (GoodPojo_Equals_HashCode_ToString) o;

            return new EqualsBuilder().append(random, that.random)
                                      .append(byteField, that.byteField)
                                      .append(shortType, that.shortType)
                                      .append(intType, that.intType)
                                      .append(longType, that.longType)
                                      .append(doubleType, that.doubleType)
                                      .append(booleanType, that.booleanType)
                                      .append(floatType, that.floatType)
                                      .append(charType, that.charType)
                                      .append(testEnum1, that.testEnum1)
                                      .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(random)
                                        .append(byteField)
                                        .append(shortType)
                                        .append(intType)
                                        .append(longType)
                                        .append(doubleType)
                                        .append(booleanType)
                                        .append(floatType)
                                        .append(charType)
                                        .append(testEnum1)
                                        .toHashCode();
        }

        public long getRandom() {
            return random;
        }

        public void setRandom(final long random) {
            this.random = random;
        }

        public byte getByteField() {
            return byteField;
        }

        public void setByteField(final byte byteField) {
            this.byteField = byteField;
        }

        public short getShortType() {
            return shortType;
        }

        public void setShortType(final short shortType) {
            this.shortType = shortType;
        }

        public int getIntType() {
            return intType;
        }

        public void setIntType(final int intType) {
            this.intType = intType;
        }

        public long getLongType() {
            return longType;
        }

        public void setLongType(final long longType) {
            this.longType = longType;
        }

        public double getDoubleType() {
            return doubleType;
        }

        public void setDoubleType(final double doubleType) {
            this.doubleType = doubleType;
        }

        public boolean isBooleanType() {
            return booleanType;
        }

        public void setBooleanType(final boolean booleanType) {
            this.booleanType = booleanType;
        }

        public float getFloatType() {
            return floatType;
        }

        public void setFloatType(final float floatType) {
            this.floatType = floatType;
        }

        public char getCharType() {
            return charType;
        }

        public void setCharType(final char charType) {
            this.charType = charType;
        }

        public TestEnum1 getTestEnum1() {
            return testEnum1;
        }

        public void setTestEnum1(final TestEnum1 testEnum1) {
            this.testEnum1 = testEnum1;
        }
    }


}
