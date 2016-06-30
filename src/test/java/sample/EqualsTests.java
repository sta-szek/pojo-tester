package sample;


import org.pojo.tester.EqualsTester;
import test.equals.BadPojoEquals_NotConsistent;

public class EqualsTests {

    public static void main(final String[] args) {
        final EqualsTester equalsTester = new EqualsTester();
        equalsTester.test(BadPojoEquals_NotConsistent.class);
    }
}
