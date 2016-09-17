package pl.pojo.tester.internal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.GetterNotFoundException;
import pl.pojo.tester.api.SetterNotFoundException;

import static classesForTest.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class MethodUtilsTest {

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Setter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromSettersClass("a");
        final Field fieldB = fieldFromSettersClass("b");
        final Field fieldC = fieldFromSettersClass("c");
        final Field fieldD = fieldFromSettersClass("d");
        final Field fieldF = fieldFromSettersClass("f");
        final Field fieldG = fieldFromSettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldC,
                         fieldD,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Setter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Setter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> MethodUtils.findSetterFor(Setters.class, field));

            // then
            assertThat(result).isInstanceOf(SetterNotFoundException.class);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Getter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromGettersClass("a");
        final Field fieldB = fieldFromGettersClass("b");
        final Field fieldD = fieldFromGettersClass("d");
        final Field fieldE = fieldFromGettersClass("e");
        final Field fieldF = fieldFromGettersClass("f");
        final Field fieldG = fieldFromGettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldD,
                         fieldE,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Getter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Getter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> MethodUtils.findGetterFor(Getters.class, field));

            // then
            assertThat(result).isInstanceOf(GetterNotFoundException.class);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Getter() throws NoSuchFieldException, NoSuchMethodException {
        final Field field1 = fieldFromGettersClass("getter1");
        final Field field2 = fieldFromGettersClass("getter2");
        final Field field3 = fieldFromGettersClass("getter3");
        final Field field4 = fieldFromGettersClass("getter4");
        final Field field5 = fieldFromGettersClass("getter5");
        final Field field6 = fieldFromGettersClass("getter6");
        final Field field7 = fieldFromGettersClass("getter7");
        final Field field8 = fieldFromGettersClass("id");
        final Field field9 = fieldFromGettersClass("otherId");
        final Field field10 = fieldFromGettersClass("name");
        final Field field11 = fieldFromGettersClass("otherName");

        return Stream.of(new GetterTestCase(field1, Getters.class.getMethod("isGetter1")),
                         new GetterTestCase(field2, Getters.class.getMethod("hasGetter2")),
                         new GetterTestCase(field3, Getters.class.getMethod("haveGetter3")),
                         new GetterTestCase(field4, Getters.class.getMethod("containsGetter4")),
                         new GetterTestCase(field5, Getters.class.getMethod("getGetter5")),
                         new GetterTestCase(field6, Getters.class.getMethod("getGetter6")),
                         new GetterTestCase(field7, Getters.class.getMethod("getGetter7")),
                         new GetterTestCase(field8, Getters.class.getMethod("getId")),
                         new GetterTestCase(field9, Getters.class.getMethod("getOtherId")),
                         new GetterTestCase(field10, Getters.class.getMethod("getName")),
                         new GetterTestCase(field11, Getters.class.getMethod("getOtherName"))
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Getter(value)));
    }

    public Executable Should_Return_Expected_Getter(final GetterTestCase testCase) {
        return () -> {
            // when
            final Method result = MethodUtils.findGetterFor(Getters.class, testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.expectedMethod);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Setter() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException {
        final Field field1 = fieldFromSettersClass("setter1");
        final Field field2 = fieldFromSettersClass("setter2");
        final Field field3 = fieldFromSettersClass("setter3");
        final Field field4 = fieldFromSettersClass("setter4");
        final Field field5 = fieldFromSettersClass("id");
        final Field field6 = fieldFromSettersClass("otherId");
        final Field field7 = fieldFromSettersClass("name");
        final Field field8 = fieldFromSettersClass("otherName");

        return Stream.of(new SetterTestCase(field1, Setters.class.getMethod("setSetter1", int.class)),
                         new SetterTestCase(field2, Setters.class.getMethod("setSetter2", Integer.class)),
                         new SetterTestCase(field3, Setters.class.getMethod("setSetter3", Integer.class)),
                         new SetterTestCase(field4, Setters.class.getMethod("setSetter4", int.class)),
                         new SetterTestCase(field5, Setters.class.getMethod("setId", int.class)),
                         new SetterTestCase(field6, Setters.class.getMethod("setOtherId", int.class)),
                         new SetterTestCase(field7, Setters.class.getMethod("setName", String.class)),
                         new SetterTestCase(field8, Setters.class.getMethod("setOtherName", String.class))
        )
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Setter(value)));
    }

    public Executable Should_Return_Expected_Setter(final SetterTestCase testCase) {
        return () -> {
            // when
            final Method result = MethodUtils.findSetterFor(Setters.class, testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.expectedMethod);
        };
    }

    private Field fieldFromSettersClass(final String name) throws NoSuchFieldException {
        return Setters.class.getDeclaredField(name);
    }

    private Field fieldFromGettersClass(final String name) throws NoSuchFieldException {
        return Getters.class.getDeclaredField(name);
    }

    @AllArgsConstructor
    @ToString
    private class GetterTestCase {
        private Field field;
        private Method expectedMethod;
    }

    @AllArgsConstructor
    @ToString
    private class SetterTestCase {
        private Field field;
        private Method expectedMethod;
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
        private int id;
        private int otherId;
        private String name;
        private String otherName;

        public void setOtherId(final int otherId) {
            this.otherId = otherId;
        }

        public void setOtherName(final String otherName) {
            this.otherName = otherName;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setId(final int id) {
            this.id = id;
        }

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

    private class Getters {

        public boolean getter1;
        public boolean getter2;
        public boolean getter3;
        public Boolean getter4;
        public int getter5;
        public Integer getter6;
        public Boolean getter7;
        private int a;
        private int b;
        private int d;
        private boolean e;
        private boolean f;
        private boolean g;
        private int id;
        private int otherId;
        private String name;
        private String otherName;

        public String getOtherName() {
            return otherName;
        }

        public int getOtherId() {
            return otherId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getGetter6() {
            return getter6;
        }

        public Boolean getGetter7() {
            return getter7;
        }

        public boolean isGetter1() {
            return getter1;
        }

        public boolean hasGetter2() {
            return getter2;
        }

        public boolean haveGetter3() {
            return getter3;
        }

        public Boolean containsGetter4() {
            return getter4;
        }

        public int getGetter5() {
            return getter5;
        }

        public int a() {
            return 0;
        }

        public boolean e() {
            return false;
        }

        public int getB(final Object o) {
            return 0;
        }

        public int get() {
            return 0;
        }

        public boolean issG() {
            return g;
        }
    }

}
