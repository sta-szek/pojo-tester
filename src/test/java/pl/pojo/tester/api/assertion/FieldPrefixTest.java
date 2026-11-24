package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.tester.GetterTester;
import pl.pojo.tester.internal.utils.MethodUtils;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class FieldPrefixTest {

    @Test
    void Should_Match_Getters_And_Setters_After_An_M_Prefix() {
        assertPojoMethodsFor(Prefixed.class)
                .testing(GETTER, SETTER)
                .withFieldPrefix("m")
                .areWellImplemented();
    }

    @Test
    void Should_Match_Getters_And_Setters_After_An_Underscore_Prefix() {
        assertPojoMethodsFor(Underscore.class)
                .testing(GETTER, SETTER)
                .withFieldPrefix("m_")
                .areWellImplemented();
    }

    @Test
    void Should_Leave_A_Field_That_Does_Not_Start_With_The_Prefix() {
        assertPojoMethodsFor(Plain.class)
                .testing(GETTER, SETTER)
                .withFieldPrefix("m")
                .areWellImplemented();
    }

    @Test
    void Should_Not_Strip_A_Prefix_That_Is_The_Whole_Name() {
        assertPojoMethodsFor(WholeName.class)
                .testing(GETTER, SETTER)
                .withFieldPrefix("m")
                .areWellImplemented();
    }

    @Test
    void Should_Not_Strip_A_Prefix_When_The_Remainder_Is_Not_A_Property() {
        assertPojoMethodsFor(LowerRemainder.class)
                .testing(GETTER, SETTER)
                .withFieldPrefix("m")
                .areWellImplemented();
    }

    @Test
    void Should_Treat_A_Null_Prefix_As_No_Prefix() throws NoSuchFieldException {
        final GetterTester tester = new GetterTester();
        tester.setFieldPrefix(null);
        assertThat(tester.getFieldPrefix()).isEmpty();

        final Field name = Plain.class.getDeclaredField("name");
        assertThat(MethodUtils.findGetterFor(Plain.class, name, null).getName()).isEqualTo("getName");
        assertThat(MethodUtils.findSetterFor(Plain.class, name, null).getName()).isEqualTo("setName");
    }

    static class Prefixed {
        private String mName;

        public String getName() {
            return mName;
        }

        public void setName(final String name) {
            mName = name;
        }
    }

    static class Underscore {
        private String m_name;

        public String getName() {
            return m_name;
        }

        public void setName(final String name) {
            m_name = name;
        }
    }

    static class Plain {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    static class WholeName {
        private String m;

        public String getM() {
            return m;
        }

        public void setM(final String value) {
            m = value;
        }
    }

    static class LowerRemainder {
        private String mname;

        public String getMname() {
            return mname;
        }

        public void setMname(final String value) {
            mname = value;
        }
    }
}
