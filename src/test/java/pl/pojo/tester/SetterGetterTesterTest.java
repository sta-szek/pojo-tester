package pl.pojo.tester;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.field.DefaultFieldValueChanger;
import test.fields.Getters;
import test.fields.Setters;
import test.settergetter.BadPojoSetterGetter;
import test.settergetter.GoodPojoSetterGetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class SetterGetterTesterTest {

    @Test
    public void Should_Pass_All_Setter_Getter_Tests() {
        // given
        final Class[] classesToTest = {GoodPojoSetterGetter.class};
        final SetterGetterTester setterGetterTester = new SetterGetterTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> setterGetterTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Getter_Tests_Excluding_Fields() {
        // given
        final SetterGetterTester setterGetterTester = new SetterGetterTester();
        final Class<?> clazz = BadPojoSetterGetter.class;
        final List<String> excludedFields = newArrayList("c", "d", "charY");

        // when
        final Throwable result = catchThrowable(() -> setterGetterTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Getter_Tests_Including_Fields() {
        // given
        final SetterGetterTester setterGetterTester = new SetterGetterTester();
        final Class<?> clazz = BadPojoSetterGetter.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> setterGetterTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoSetterGetter.class,
                                       Setters.class,
                                       Getters.class};
        final SetterGetterTester setterGetterTester = new SetterGetterTester();

        // when
        final Throwable result = catchThrowable(() -> setterGetterTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(SetterNotFoundException.class);
    }

}
