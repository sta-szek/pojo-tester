package pl.pojo.tester.api.assertion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import lombok.Data;
import lombok.Delegate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.NonFinal;
import lombok.experimental.PackagePrivate;
import lombok.experimental.StandardException;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import lombok.experimental.UtilityClass;
import lombok.experimental.WithBy;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.FieldPredicate;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.SETTER;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

/**
 * Lombok annotations that generate the methods this library tests.
 * Logger bindings other than {@code java.util.logging} and SLF4J need a library that is not on the test classpath
 * ({@code @Log4j}, {@code @Log4j2}, {@code @Flogger}, {@code @JBossLog}, {@code @XSlf4j}, {@code @Jacksonized}).
 */
class LombokPojoTest {

    @Test
    void Should_Test_Getter() {
        assertPojoMethodsFor(GetterOnly.class).testing(GETTER).areWellImplemented();
    }

    @Test
    void Should_Test_Setter() {
        assertPojoMethodsFor(SetterOnly.class).testing(SETTER, GETTER).areWellImplemented();
    }

    @Test
    void Should_Test_ToString() {
        assertPojoMethodsFor(ToStringOnly.class).testing(TO_STRING).areWellImplemented();
    }

    @Test
    void Should_Test_EqualsAndHashCode() {
        assertPojoMethodsFor(EqualsOnly.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Test_Data() {
        assertPojoMethodsFor(DataPojo.class).areWellImplemented();
    }

    @Test
    void Should_Test_Value_With_With_And_NonFinal() {
        assertPojoMethodsFor(ValuePojo.class)
                .changingFinalFields()
                .testing(GETTER, EQUALS, HASH_CODE, TO_STRING, SETTER)
                .areWellImplemented();
        assertThat(new ValuePojo("a", "b").withId("c").getId()).isEqualTo("c");
        assertThat(new ValuePojo("a", "b").withNameBy(name -> name + "x").getName()).isEqualTo("bx");
    }

    @Test
    void Should_Test_Data_And_Builder_On_A_Static_Nested_Class() {
        assertPojoMethodsFor(Questionnaire.class).areWellImplemented();
        assertPojoMethodsFor(Questionnaire.Answer.class).areWellImplemented();
    }

    @Test
    void Should_Test_Constructors() {
        assertPojoMethodsFor(NoArgs.class).testing(CONSTRUCTOR, GETTER).areWellImplemented();
        assertPojoMethodsFor(AllArgs.class).testing(CONSTRUCTOR, EQUALS, HASH_CODE).areWellImplemented();
        assertPojoMethodsFor(Required.class).testing(CONSTRUCTOR, GETTER, EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Test_Chained_And_Fluent_Accessors() {
        assertPojoMethodsFor(Chained.class).testing(GETTER, SETTER).areWellImplemented();
        assertPojoMethodsFor(Fluent.class).usingFluentAccessors().testing(GETTER, SETTER).areWellImplemented();
    }

    @Test
    void Should_Test_Builder_Default_And_Singular() {
        assertPojoMethodsFor(Built.class).testing(GETTER, SETTER, EQUALS, HASH_CODE, TO_STRING).areWellImplemented();
    }

    @Test
    void Should_Test_SuperBuilder() {
        assertPojoMethodsFor(Child.class)
                .create(Child.class, () -> Child.builder().id("a").name("b").build())
                .testing(GETTER, EQUALS, HASH_CODE)
                .areWellImplemented();
    }

    @Test
    void Should_Test_Excluded_Fields_Field_Defaults_And_Field_Name_Constants() {
        assertPojoMethodsFor(Named.class, FieldPredicate.exclude("secret"))
                .testing(EQUALS, HASH_CODE, GETTER, SETTER)
                .areWellImplemented();
        assertThat(Named.Fields.id).isEqualTo("id");
    }

    @Test
    void Should_Test_Delegate_And_NonNull() {
        assertPojoMethodsFor(Delegating.class).testing(EQUALS, HASH_CODE, GETTER, SETTER).areWellImplemented();
    }

    @Test
    void Should_Test_StandardException() {
        assertPojoMethodsFor(Boom.class).testing(CONSTRUCTOR).areWellImplemented();
    }

    @Test
    void Should_Refuse_To_Instantiate_A_Utility_Class() throws Exception {
        final Constructor<Tools> constructor = Tools.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
        constructor.setAccessible(true);
        assertThatThrownBy(constructor::newInstance).hasRootCauseInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void Should_Leave_A_Package_Private_Field_At_That_Access() throws Exception {
        assertThat(Modifier.isPrivate(Hidden.class.getDeclaredField("id").getModifiers())).isFalse();
        assertThat(Modifier.isPublic(Hidden.class.getDeclaredField("id").getModifiers())).isFalse();
    }

    @Test
    void Should_Run_Method_Level_Annotations() {
        new MethodLevel().usesThem();
    }

    @Getter
    static class GetterOnly {
        private String name;
    }

    @Getter
    @Setter
    static class SetterOnly {
        private String name;
    }

    @ToString
    static class ToStringOnly {
        private String name;
    }

    @EqualsAndHashCode
    static class EqualsOnly {
        private String name;
        private int age;
    }

    @Data
    static class DataPojo {
        private String name;
        private int age;
    }

    @Value
    @With
    @WithBy
    static class ValuePojo {
        String id;

        @NonFinal
        @Setter
        String name;
    }

    @Data
    @Builder
    @EqualsAndHashCode
    @Slf4j
    static class Questionnaire {
        private String questionnaireId;
        private String id;
        private String label;
        private String text;
        private String comment;
        private List<String> tags;
        private Answer answer;

        @Data
        @Builder
        @EqualsAndHashCode
        @Log
        public static class Answer {
            private String type;
            private List<String> options;
        }
    }

    @NoArgsConstructor
    @Getter
    static class NoArgs {
        private String name;
    }

    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    static class AllArgs {
        private String name;
        private int age;
    }

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    static class Required {
        private final String name;
        private int age;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    static class Chained {
        private String name;
    }

    @Getter
    @Setter
    @Accessors(fluent = true)
    static class Fluent {
        private String name;
    }

    @Data
    @Builder
    static class Built {
        @Builder.Default
        private String id = "unset";

        @Singular
        private List<String> tags;

        @Tolerate
        public void setId(final int id) {
            this.id = Integer.toString(id);
        }
    }

    @SuperBuilder
    @Getter
    @EqualsAndHashCode
    static class Parent {
        private String id;
    }

    @SuperBuilder
    @Getter
    @EqualsAndHashCode(callSuper = true)
    static class Child extends Parent {
        private String name;
    }

    @Data
    @FieldDefaults(level = lombok.AccessLevel.PRIVATE)
    @FieldNameConstants
    static class Named {
        String id;

        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        String secret;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    static class Delegating {
        @Delegate
        private List<String> names = new ArrayList<>();
        private String id;

        public void rename(@NonNull final String id) {
            this.id = id;
        }
    }

    @StandardException
    static class Boom extends Exception {
    }

    @UtilityClass
    static class Tools {
        String id() {
            return "x";
        }
    }

    @FieldDefaults(level = lombok.AccessLevel.PUBLIC)
    static class Hidden {
        @PackagePrivate
        String id;
    }

    static class MethodLevel {
        @Synchronized
        @SneakyThrows
        void usesThem() {
            @Cleanup final Closeable closeable = () -> {
            };
            closeable.hashCode();
            assertThat(checked()).isEqualTo("a");
            new LockedMethod().locked();
        }

        @lombok.CheckReturnValue
        private String checked() {
            final lombok.val fixed = "a";
            lombok.var mutable = "b";
            mutable = fixed;
            return mutable;
        }
    }

    static class LockedMethod {
        @lombok.Locked
        void locked() {
            // Empty on purpose: @Locked is the annotation under test, not the body.
        }
    }
}
