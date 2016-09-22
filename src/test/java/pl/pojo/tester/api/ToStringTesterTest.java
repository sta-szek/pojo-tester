package pl.pojo.tester.api;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.tostring.ToStringWithoutField;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.AssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class ToStringTesterTest {

    @Test
    public void Should_Pass_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final ToStringTester toStringTester = new ToStringTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> toStringTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("testEnum");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b", "obj");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {ToStringWithoutField.class};
        final ToStringTester toStringTester = new ToStringTester();

        // when
        final Throwable result = catchThrowable(() -> toStringTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("a");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }


}
