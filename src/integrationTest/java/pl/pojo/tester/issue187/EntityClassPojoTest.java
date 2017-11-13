package pl.pojo.tester.issue187;


import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.CONSTRUCTOR;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

class EntityClassPojoTest {

    @Test
    void Should_Test_Entity_Class() {
        final ConstructorParameters constructorParameters = new ConstructorParameters(
                new Object[]{ LocalDateTime.now(), ZoneOffset.MAX, ZoneId.systemDefault() },
                new Class[]{ LocalDateTime.class, ZoneOffset.class, ZoneId.class });

        for (int i = 0; i < 100; i++) {
            assertPojoMethodsFor(EntityClass.class).testing(GETTER, TO_STRING, CONSTRUCTOR, EQUALS, HASH_CODE)
                                                   .create(ZonedDateTime.class, constructorParameters)
                                                   .areWellImplemented();
        }
    }

}
