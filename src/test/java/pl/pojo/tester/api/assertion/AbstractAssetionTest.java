package pl.pojo.tester.api.assertion;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.equals.BadPojoEqualsItself;
import com.google.common.collect.Sets;
import matchers.MapMatcher;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.api.EqualsTester;
import pl.pojo.tester.api.HashCodeTester;
import pl.pojo.tester.internal.assertion.AssertionError;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.getInternalState;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(JUnitPlatform.class)
public class AbstractAssetionTest {

    @Test
    public void Should_Set_Field_Value_Changer() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final AbstractFieldValueChanger expectedFieldsValuesChanger = DefaultFieldValueChanger.INSTANCE;

        // when
        abstractAssetion.using(expectedFieldsValuesChanger);
        final AbstractFieldValueChanger result = getInternalState(abstractAssetion, "abstractFieldValueChanger");

        // then
        assertThat(result).isEqualTo(expectedFieldsValuesChanger);
    }

    @Test
    public void Should_Add_Equals_Tester() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final EqualsTester expectedTester = new EqualsTester();

        // when
        abstractAssetion.testing(Method.EQUALS);

        // then
        assertThat(abstractAssetion.testers).usingRecursiveFieldByFieldElementComparator()
                                            .containsExactly(expectedTester);
    }

    @Test
    public void Should_Add_Equals_And_Hash_Code_Testers() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final EqualsTester expectedTester1 = new EqualsTester();
        final HashCodeTester expectedTester2 = new HashCodeTester();

        // when
        abstractAssetion.testing(Method.EQUALS, Method.HASH_CODE);

        // then
        assertThat(abstractAssetion.testers).usingRecursiveFieldByFieldElementComparator()
                                            .containsExactly(expectedTester1, expectedTester2);
    }

    @Test
    public void Should_Not_Throw_Exception_When_Class_Has_All_Methods_Well_Implemented() {
        // given
        final Class<GoodPojo_Equals_HashCode_ToString> classUnderTest = GoodPojo_Equals_HashCode_ToString.class;

        // when
        final Throwable result = catchThrowable(() -> Assertions.assertPojoMethodsForAll(classUnderTest)
                                                                .areWellImplemented());

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_Class_Has_Method_Implemented_In_Wrong_Way() {
        // given
        final Class<BadPojoEqualsItself> classUnderTest = BadPojoEqualsItself.class;

        // when
        final Throwable result = catchThrowable(() -> Assertions.assertPojoMethodsFor(classUnderTest)
                                                                .testing(Method.EQUALS)
                                                                .areWellImplemented());

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Set_Field_Value_Changer_To_Testers() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final AbstractFieldValueChanger expectedFieldsValuesChanger = DefaultFieldValueChanger.INSTANCE;
        final EqualsTester equalsTester = mock(EqualsTester.class);
        setInternalState(abstractAssetion, "testers", Sets.newHashSet(equalsTester));
        abstractAssetion.using(expectedFieldsValuesChanger);

        // when
        abstractAssetion.areWellImplemented();

        // then
        verify(equalsTester, times(1)).setFieldValuesChanger(expectedFieldsValuesChanger);
    }

    @Test
    public void Should_Set_User_Defined_Class_And_Constructor_Paramters_To_Tester() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final EqualsTester equalsTester = mock(EqualsTester.class);
        setInternalState(abstractAssetion, "testers", Sets.newHashSet(equalsTester));
        final Class<String> expectedClass = String.class;
        final Object[] expectedArguments = {'c', 'h', 'a', 'r'};
        final Class[] expectedTypes = {char.class, char.class, char.class, char.class};
        final ConstructorParameters expectedConstructorParameters = new ConstructorParameters(expectedArguments, expectedTypes);
        abstractAssetion.create(expectedClass, expectedConstructorParameters);

        // when
        abstractAssetion.areWellImplemented();

        // then
        verify(equalsTester, times(1)).setUserDefinedConstructors(argThat(new MapMatcher(expectedClass, expectedConstructorParameters)));
    }

    @Test
    public void Should_Call_Next_Create_Method() {
        // given
        final AbstractAssetion abstractAssetion = spy(new AbstractAssetionImplementation());
        final EqualsTester equalsTester = mock(EqualsTester.class);
        setInternalState(abstractAssetion, "testers", Sets.newHashSet(equalsTester));
        final Class<String> expectedClass = String.class;
        final Object[] expectedArguments = {'c', 'h', 'a', 'r'};
        final Class[] expectedTypes = {char.class, char.class, char.class, char.class};
        final ConstructorParameters expectedConstructorParameters = new ConstructorParameters(expectedArguments, expectedTypes);
        abstractAssetion.create(expectedClass, expectedArguments, expectedTypes);

        // when
        abstractAssetion.areWellImplemented();

        // then
        verify(abstractAssetion).create(eq(expectedClass), eq(expectedConstructorParameters));
    }

    @Test
    public void Should_Set_User_Defined_Class_And_Constructor_Paramters_To_Tester_Using_Class_Name() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final EqualsTester equalsTester = mock(EqualsTester.class);
        setInternalState(abstractAssetion, "testers", Sets.newHashSet(equalsTester));
        final Class<?> expectedClass = String.class;
        final Object[] expectedArguments = {'c', 'h', 'a', 'r'};
        final Class[] expectedTypes = {char.class, char.class, char.class, char.class};
        final ConstructorParameters expectedConstructorParameters = new ConstructorParameters(expectedArguments, expectedTypes);
        abstractAssetion.create("java.lang.String", expectedConstructorParameters);

        // when
        abstractAssetion.areWellImplemented();

        // then
        verify(equalsTester, times(1)).setUserDefinedConstructors(argThat(new MapMatcher(expectedClass, expectedConstructorParameters)));
    }

    @Test
    public void Should_Call_Next_Create_Method_Using_Class_Name() {
        // given
        final AbstractAssetion abstractAssetion = spy(new AbstractAssetionImplementation());
        final EqualsTester equalsTester = mock(EqualsTester.class);
        setInternalState(abstractAssetion, "testers", Sets.newHashSet(equalsTester));
        final Object[] expectedArguments = {'c', 'h', 'a', 'r'};
        final Class[] expectedTypes = {char.class, char.class, char.class, char.class};
        final ConstructorParameters expectedConstructorParameters = new ConstructorParameters(expectedArguments, expectedTypes);
        final String expectedClassName = "java.lang.String";
        abstractAssetion.create(expectedClassName, expectedArguments, expectedTypes);

        // when
        abstractAssetion.areWellImplemented();

        // then
        verify(abstractAssetion).create(eq(expectedClassName), eq(expectedConstructorParameters));
    }

    private class AbstractAssetionImplementation extends AbstractAssetion {

        @Override
        protected void testImplementation() {
            // not needed for tests
        }
    }
}
