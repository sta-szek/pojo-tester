package sample;


import org.pojo.tester.EqualsTester;
import test.equals.BadPojoEquals_NotConsistent;

public class Usage {

    public static void main(final String[] args) {
        final EqualsTester equalsTester = new EqualsTester();
        equalsTester.testAll(BadPojoEquals_NotConsistent.class);
    }
}
