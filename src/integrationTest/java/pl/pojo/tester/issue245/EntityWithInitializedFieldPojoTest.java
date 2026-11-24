package pl.pojo.tester.issue245;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class EntityWithInitializedFieldPojoTest {

    @Test
    void Should_Test_Entity_Whose_Field_Is_Initialized_At_Declaration() {
        assertPojoMethodsFor(ChildEntity.class).testing(EQUALS, HASH_CODE)
                                               .areWellImplemented();
        assertPojoMethodsFor(EntityUnderTest.class).testing(EQUALS, HASH_CODE)
                                                   .areWellImplemented();
    }

}
