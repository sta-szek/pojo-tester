package org.pojo.tester;

import org.junit.Test;
import test.GoodPojo_Equals_HashCode_ToString;
import test.hashcode.BadPojoHashCode;
import test.hashcode.BadPojoHashCodeDifferentObjectSameType;
import test.hashcode.BadPojoHashCodeItself;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.pojo.tester.FieldPredicate.exclude;
import static org.pojo.tester.FieldPredicate.include;


public class HashCodeTesterTest {

    @Test
    public void Should_Pass_All_HashCode_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_HashCode_Tests_Excluding_Fields() {
        // given
        final HashCodeTester hashCodeTester = new HashCodeTester();
        final Class<?> clazz = BadPojoHashCode.class;
        final List<String> excludedFields = newArrayList("increment3", "increment4");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_HashCode_Tests_Including_Fields() {
        // given
        final HashCodeTester hashCodeTester = new HashCodeTester();
        final Class<?> clazz = BadPojoHashCode.class;
        final List<String> includedFields = newArrayList("increment1", "increment2");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Itself_Test() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class,
                                       BadPojoHashCodeDifferentObjectSameType.class,
                                       BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Object_With_Same_Type() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeDifferentObjectSameType.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testHashCodeMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

}
