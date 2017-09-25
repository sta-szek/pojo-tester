package pl.pojo.tester.issue212;


import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class DefensiveCopyTest {

    @Test
    public void Should_Test_Getters_And_Setters_In_Defensive_Copy_Class() {
        assertPojoMethodsFor(DefensiveCopy.class)
                .testing(Method.GETTER)
                .testing(Method.SETTER)
                .areWellImplemented();
    }
}

