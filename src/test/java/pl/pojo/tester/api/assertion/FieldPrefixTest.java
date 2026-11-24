package pl.pojo.tester.api.assertion;

import org.junit.jupiter.api.Test;

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
}
