package org.pojo.tester;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.assertion.AssertionError;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import org.powermock.reflect.Whitebox;
import test.GoodPojo_Equals_HashCode_ToString;
import test.equals.BadPojoEqualsItself;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class AbstractAssetionTest {

    @Test
    public void Should_Set_Field_Value_Changer() {
        // given
        final AbstractAssetion abstractAssetion = new AbstractAssetionImplementation();
        final AbstractFieldValueChanger expectedFieldsValuesChanger = DefaultFieldValueChanger.INSTANCE;

        // when
        abstractAssetion.using(expectedFieldsValuesChanger);
        final AbstractFieldValueChanger result = Whitebox.getInternalState(abstractAssetion, "abstractFieldValueChanger");

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
        final Throwable result = catchThrowable(() -> Assetions.assertPojoMethodsFor(classUnderTest)
                                                               .areWellImplemented());

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_Class_Has_Method_Implemented_In_Wrong_Way() {
        // given
        final Class<BadPojoEqualsItself> classUnderTest = BadPojoEqualsItself.class;

        // when
        final Throwable result = catchThrowable(() -> Assetions.assertPojoMethodsFor(classUnderTest)
                                                               .testing(Method.EQUALS)
                                                               .areWellImplemented());

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    private class AbstractAssetionImplementation extends AbstractAssetion {

        @Override
        protected void testImplementation() {
            // not needed for tests
        }
    }
}
