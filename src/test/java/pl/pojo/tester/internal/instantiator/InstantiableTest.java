package pl.pojo.tester.internal.instantiator;

import classesForTest.ClassContainingStaticClasses;
import classesForTest.Constructor_Field;
import classesForTest.Constructor_Stream;
import classesForTest.Constructor_Thread;
import classesForTest.EmptyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import pl.pojo.tester.api.AbstractObjectInstantiator;
import pl.pojo.tester.internal.field.date.DefaultDateAndTimeFieldValueChanger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class InstantiableTest {

    private static final List<AbstractObjectInstantiator> INSTANTIATORS = new LinkedList<>();

    @BeforeAll
    static void beforeAll() {
        INSTANTIATORS.add(new SupplierInstantiator(UserDefinedClass.class, () -> null));
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_Expected_Instantiator_For_Class() {
        return Stream.of(new ClassInstantiator(Serializable.class, ProxyInstantiator.class),
                         new ClassInstantiator(Override.class, ProxyInstantiator.class),
                         new ClassInstantiator(Runnable.class, ProxyInstantiator.class),
                         new ClassInstantiator(EmptyEnum.class, EnumInstantiator.class),
                         new ClassInstantiator(Instantiable.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(DefaultDateAndTimeFieldValueChanger.class,
                                               BestConstructorInstantiator.class),
                         new ClassInstantiator(Constructor_Field.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(Constructor_Stream.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(Constructor_Thread.class, BestConstructorInstantiator.class),
                         new ClassInstantiator(String.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(UserDefinedClass.class, SupplierInstantiator.class),
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
                         new ClassInstantiator(Boolean.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Byte.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Character.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Double.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Float.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Integer.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Long.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(Short.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(boolean.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(byte.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(char.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(double.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(float.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(int.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(long.class, JavaTypeInstantiator.class),
                         new ClassInstantiator(short.class, JavaTypeInstantiator.class),

                         new ClassInstantiator(Stream.class, CollectionInstantiator.class),
                         new ClassInstantiator(Stack.class, CollectionInstantiator.class),
                         new ClassInstantiator(Vector.class, CollectionInstantiator.class),
                         new ClassInstantiator(ArrayList.class, CollectionInstantiator.class),
                         new ClassInstantiator(LinkedList.class, CollectionInstantiator.class),
                         new ClassInstantiator(LinkedHashSet.class, CollectionInstantiator.class),
                         new ClassInstantiator(HashSet.class, CollectionInstantiator.class),
                         new ClassInstantiator(TreeSet.class, CollectionInstantiator.class),
                         new ClassInstantiator(Iterator.class, CollectionInstantiator.class),
                         new ClassInstantiator(LinkedHashMap.class, CollectionInstantiator.class),
                         new ClassInstantiator(HashMap.class, CollectionInstantiator.class),
                         new ClassInstantiator(Hashtable.class, CollectionInstantiator.class),
                         new ClassInstantiator(NavigableMap.class, CollectionInstantiator.class),
                         new ClassInstantiator(TreeMap.class, CollectionInstantiator.class),
                         new ClassInstantiator(SortedMap.class, CollectionInstantiator.class),
                         new ClassInstantiator(Map.class, CollectionInstantiator.class),
                         new ClassInstantiator(NavigableSet.class, CollectionInstantiator.class),
                         new ClassInstantiator(SortedSet.class, CollectionInstantiator.class),
                         new ClassInstantiator(Set.class, CollectionInstantiator.class),
                         new ClassInstantiator(List.class, CollectionInstantiator.class),
                         new ClassInstantiator(Deque.class, CollectionInstantiator.class),
                         new ClassInstantiator(Queue.class, CollectionInstantiator.class),
                         new ClassInstantiator(Collection.class, CollectionInstantiator.class),
                         new ClassInstantiator(Iterable.class, CollectionInstantiator.class),

                         new ClassInstantiator(ClassContainingStaticClasses.class,
                                               DefaultConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PublicConstructor.class,
                                               DefaultConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PackageConstructor.class,
                                               BestConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_ProtectedConstructor
                                                       .class,
                                               BestConstructorInstantiator.class),
                         new ClassInstantiator(ClassContainingStaticClasses.NestedStaticClass_PrivateConstructor.class,
                                               BestConstructorInstantiator.class))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.clazz.getName()),
                                               Should_Return_Expected_Instantiator_For_Class(value)));
    }

    private Executable Should_Return_Expected_Instantiator_For_Class(final ClassInstantiator testCase) {
        return () -> {
            // when
            final Object result = Instantiable.forClass(testCase.clazz, INSTANTIATORS);

            // then
            assertThat(result).isInstanceOf(testCase.instantiator);
        };
    }

    @Test
    void Should_Instantiate_Two_Classes() {
        // given
        final Class[] classesToInstantiate = { A.class, B.class };

        // when
        final Object[] result = Instantiable.instantiateClasses(classesToInstantiate, new ArrayList<>());

        // then
        assertThat(result).extracting(Object::getClass)
                          .containsExactlyInAnyOrder(classesToInstantiate);
    }

    @Test
    void Should_Return_Instantiator_Defined_By_User() {
        // given
        final List<AbstractObjectInstantiator> instantiators = new ArrayList<>();
        final Class<?> clazz = A.class;
        final SupplierInstantiator expectedInstantiator = new SupplierInstantiator(clazz, () -> new Object[0]);
        instantiators.add(expectedInstantiator);

        // when
        final AbstractObjectInstantiator result = Instantiable.forClass(clazz, instantiators);

        // then
        assertThat(result).isEqualTo(expectedInstantiator);
    }

    @Test
    void Should_Return_Proxy_Instantiator_If_Class_Qualifies_For_Proxy() {
        // given
        final Class<?> clazz = Abstract.class;

        // when
        final AbstractObjectInstantiator result = Instantiable.forClass(clazz, new ArrayList<>());

        // then
        assertThat(result).isInstanceOf(ProxyInstantiator.class);
    }

    @AllArgsConstructor
    private class ClassInstantiator {
        private Class<?> clazz;
        private Class<?> instantiator;

    }

    private class UserDefinedClass {

    }

    @Data
    private class A {
        int a;
        B b;
    }

    @Data
    private class B {
        A a;
        B b;
    }

    @Data
    private abstract class Abstract {
        A a;
        B b;
    }
}
