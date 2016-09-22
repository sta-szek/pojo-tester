package pl.pojo.tester.api;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.hashcode.BadPojoHashCode;
import classesForTest.hashcode.BadPojoHashCodeDifferentObjectSameType;
import classesForTest.hashcode.BadPojoHashCodeItself;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.hashcode.HashCodeAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.FieldPredicate.include;

@RunWith(JUnitPlatform.class)
public class HashCodeTesterTest {

    @Test
    public void Should_Pass_All_HashCode_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final HashCodeTester hashCodeTester = new HashCodeTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

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
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, exclude(excludedFields)));

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
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Itself_Test() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class,
                                       BadPojoHashCodeDifferentObjectSameType.class,
                                       BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Object_With_Same_Type() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeDifferentObjectSameType.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

}
