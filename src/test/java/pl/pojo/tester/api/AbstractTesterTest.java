package pl.pojo.tester.api;

import helpers.ClassAndFieldPredicatePairArgumentMatcher;
import helpers.RecursivelyEqualArgumentMatcher;
import helpers.StringPredicateArgumentMatcher;
import java.util.HashMap;
import java.util.function.Predicate;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ObjectGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)
public class AbstractTesterTest {

    @Test
    public void Should_Call_Test_With_Expected_Predicate() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;

        // when
        abstractTester.test(clazz);

        // then
        verify(abstractTester).test(eq(clazz), argThat(new StringPredicateArgumentMatcher()));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pair() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;
        final Predicate<String> predicate = string -> string.equals("a");

        // when
        abstractTester.test(clazz, predicate);

        // then
        verify(abstractTester).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(clazz, "a")));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pairs() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);
        final Class<A> clazz = A.class;

        final ClassAndFieldPredicatePair expectedParameter = new ClassAndFieldPredicatePair(clazz);

        // when
        abstractTester.testAll(clazz);

        // then
        verify(abstractTester).testAll(argThat(new RecursivelyEqualArgumentMatcher(expectedParameter)));
    }

    @Test
    public void Should_Call_Test_With_Expected_Class_And_Field_Predicate_Pairs_Two_Times() {
        // given
        final AbstractTester abstractTester = mock(AbstractTester.class, Mockito.CALLS_REAL_METHODS);

        final Class<A> aClazz = A.class;
        final Class<B> bClazz = B.class;
        final ClassAndFieldPredicatePair pair1 = new ClassAndFieldPredicatePair(aClazz);
        final ClassAndFieldPredicatePair pair2 = new ClassAndFieldPredicatePair(bClazz);

        // when
        abstractTester.testAll(pair1, pair2);

        // then
        verify(abstractTester, times(1)).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")));
        verify(abstractTester, times(1)).test(argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(aClazz, "a")),
                                              argThat(new ClassAndFieldPredicatePairArgumentMatcher(bClazz, "b")));
    }

    @Test
    public void Should_Create_New_Object_Generator_When_Set_Field_Value_Changer() {
        // given
        final AbstractTester abstractTester = new AbstractTesterImplementation();
        final AbstractFieldValueChanger fieldValuesChanger = DefaultFieldValueChanger.INSTANCE;
        final ObjectGenerator beforeChange = getInternalState(abstractTester, "objectGenerator");

        // when
        abstractTester.setFieldValuesChanger(fieldValuesChanger);
        final ObjectGenerator afterChange = getInternalState(abstractTester, "objectGenerator");

        // then
        assertThat(beforeChange).isNotEqualTo(afterChange);
    }

    @Test
    public void Should_Create_New_Object_Generator_When_User_Defined_Class_And_Constructor() {
        // given
        final AbstractTester abstractTester = new AbstractTesterImplementation();
        final ObjectGenerator beforeChange = getInternalState(abstractTester, "objectGenerator");

        // when
        abstractTester.setUserDefinedConstructors(new HashMap<>());
        final ObjectGenerator afterChange = getInternalState(abstractTester, "objectGenerator");

        // then
        assertThat(beforeChange).isNotEqualTo(afterChange);
    }

    @Test
    public void Should_Equal_Itself() {
        // given
        final AbstractTester abstractTester = new AbstractTesterImplementation();

        // when
        final boolean result = abstractTester.equals(abstractTester);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Not_Equal_Other_Object_With_Same_Values() {
        // given
        final AbstractTester abstractTester1 = new AbstractTesterImplementation();
        final AbstractTester abstractTester2 = new AbstractTesterImplementation();

        // when
        final boolean result = abstractTester1.equals(abstractTester2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Not_Equal_Null() {
        // given
        final AbstractTester abstractTester = new AbstractTesterImplementation();

        // when
        final boolean result = abstractTester.equals(null);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Not_Equal_Other_Object_With_Different_Values() {
        // given
        final AbstractTester abstractTester1 = new AbstractTesterImplementation();
        final AbstractTester abstractTester2 = new AbstractTesterImplementation(null);

        // when
        final boolean result = abstractTester1.equals(abstractTester2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Not_Equal_Other_Class() {
        // given
        final AbstractTester abstractTester1 = new AbstractTesterImplementation();

        // when
        final boolean result = abstractTester1.equals(String.class);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Generate_Same_Hash_Codes() {
        // given
        final AbstractTester abstractTester1 = new AbstractTesterImplementation();

        // when
        final int result1 = abstractTester1.hashCode();
        final int result2 = abstractTester1.hashCode();

        // then
        assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Codes_For_Every_New_Instance() {
        // given
        final AbstractTester abstractTester1 = new AbstractTesterImplementation();
        final AbstractTester abstractTester2 = new AbstractTesterImplementation();

        // when
        final int result1 = abstractTester1.hashCode();
        final int result2 = abstractTester2.hashCode();

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Data
    private class A {
        int a;
    }

    @Data
    private class B {
        int b;
    }

    class AbstractTesterImplementation extends AbstractTester {

        public AbstractTesterImplementation() {
        }

        public AbstractTesterImplementation(final AbstractFieldValueChanger o) {
            super(o);
        }

        @Override
        public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
            // not needed for tests
        }
    }
}
