package pl.pojo.tester.api;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.equals.BadPojoEqualsDifferentObjectSameType;
import classesForTest.equals.BadPojoEqualsDifferentType;
import classesForTest.equals.BadPojoEqualsItself;
import classesForTest.equals.BadPojoEqualsNull;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.AssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class EqualsTesterTest {

    @Test
    public void Should_Pass_All_Equals_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final EqualsTester equalsTester = new EqualsTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Equals_Tests_Excluding_Fields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> excludedFields = newArrayList("notIncludedToEqual_byteField", "notIncludedToEqual_shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Equals_Tests_Including_Fields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> includedFields = newArrayList("byteField", "shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Null_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Itself_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Type_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class, BadPojoEqualsDifferentType.class, BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Object_With_Same_Type() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentObjectSameType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

}
