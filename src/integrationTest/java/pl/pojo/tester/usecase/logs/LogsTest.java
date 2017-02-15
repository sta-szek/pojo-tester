package pl.pojo.tester.usecase.logs;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

public class LogsTest {

    @Test
    public void Should_Test_Class_With_Static_Fields() {
        // given

        // when

        // then
        assertPojoMethodsForAll(A.class, B.class, C.class).areWellImplemented();
    }


}
