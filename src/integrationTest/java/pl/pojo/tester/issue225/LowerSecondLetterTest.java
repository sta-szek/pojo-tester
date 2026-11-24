package pl.pojo.tester.issue225;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class LowerSecondLetterTest {

    @Test
    void Should_Match_GetxCoordinate() {
        assertPojoMethodsFor(Point.class).testing(GETTER, SETTER).areWellImplemented();
    }
}
