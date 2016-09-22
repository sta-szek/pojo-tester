package pl.pojo.tester.api;

import java.util.List;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class SetterTesterTest {

    @Test
    public void Should_Pass_All_Setter_Tests() {
        // given
        final Class[] classesToTest = {GoodPojoSetter.class};
        final SetterTester setterTester = new SetterTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> setterTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Tests_Excluding_Fields() {
        // given
        final SetterTester setterTester = new SetterTester();
        final Class<?> clazz = BadPojoSetter.class;
        final List<String> excludedFields = newArrayList("c", "d", "charY");

        // when
        final Throwable result = catchThrowable(() -> setterTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Setter_Tests_Including_Fields() {
        // given
        final SetterTester setterTester = new SetterTester();
        final Class<?> clazz = BadPojoSetter.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> setterTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoSetter.class, Setters.class};
        final SetterTester setterTester = new SetterTester();

        // when
        final Throwable result = catchThrowable(() -> setterTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(SetterNotFoundException.class);
    }

    private class BadPojoSetter {

        public char charY = 'y';
        private int a;
        private int b;
        private int c;
        private int d;

        public void setA(final int a) {
            this.a = a;
        }

        public void setB(final int b) {
            this.b = b;
        }

        public void setX(final char x) {
        }

    }

    @Setter
    private class GoodPojoSetter {

        private int a;
        private int b;
        private int c;
        private int d;

    }

    private class Setters {

        public int setter1;
        public int setter2;
        public Integer setter3;
        public Integer setter4;
        private int a;
        private int b;
        private int c;
        private int d;
        private int f;
        private int g;

        public void set() {
        }

        public void setSetter1(final int setter1) {
            this.setter1 = setter1;
        }

        public void setSetter2(final Integer setter2) {
            this.setter2 = setter2;
        }

        public void setSetter3(final Integer setter3) {
            this.setter3 = setter3;
        }

        public void setSetter4(final int setter4) {
            this.setter4 = setter4;
        }

        public void B() {
        }

        public void setxxxC() {
        }

        public void setD() {
        }

        public Object setF(final int f) {
            return null;
        }

        public void setXXXXG(final int g) {
            this.g = g;
        }
    }
}
