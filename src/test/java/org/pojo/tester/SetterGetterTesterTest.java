package org.pojo.tester;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.field.DefaultFieldValueChanger;
import test.fields.Getters;
import test.fields.Setters;
import test.settergetter.BadPojoSetterGetter;
import test.settergetter.GoodPojoSetterGetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.pojo.tester.FieldPredicate.exclude;
import static org.pojo.tester.FieldPredicate.include;

@RunWith(JUnitPlatform.class)
public class SetterGetterTesterTest {

    @Test
    public void Should_Pass_All_Setter_Getter_Tests() {
        // given
        final Class[] classesToTest = {GoodPojoSetterGetter.class};
        final SetterGetterTester hashCodeTester = new SetterGetterTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Getter_Tests_Excluding_Fields() {
        // given
        final SetterGetterTester hashCodeTester = new SetterGetterTester();
        final Class<?> clazz = BadPojoSetterGetter.class;
        final List<String> excludedFields = newArrayList("c", "d", "charY");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Getter_Tests_Including_Fields() {
        // given
        final SetterGetterTester hashCodeTester = new SetterGetterTester();
        final Class<?> clazz = BadPojoSetterGetter.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoSetterGetter.class,
                                       Setters.class,
                                       Getters.class};
        final SetterGetterTester hashCodeTester = new SetterGetterTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(classesToTest));

        // then
        assertThat(result).isInstanceOf(SetterNotFoundException.class);
    }

}
