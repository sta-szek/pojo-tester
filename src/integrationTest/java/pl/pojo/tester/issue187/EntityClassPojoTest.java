package pl.pojo.tester.issue187;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.*;

public class EntityClassPojoTest {

    @Test
    public void Should_Test_Entity_Class() {
        for (int i = 0; i < 10000; i++) {
            assertPojoMethodsFor(EntityClass.class).testing(GETTER, TO_STRING, CONSTRUCTOR, EQUALS, HASH_CODE)
                                                   .areWellImplemented();
        }
    }

}
