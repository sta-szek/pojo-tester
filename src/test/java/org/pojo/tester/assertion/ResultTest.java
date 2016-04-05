package org.pojo.tester.assertion;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ResultTest {

    @Test
    public void shouldReturnTrueIfResultIsFailed() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = new ArrayList<>();
        final List<TestPair> failedClasses =  Lists.newArrayList(new TestPair("testName", Object.class));
        final String message = "";
        final Result result = new Result(testedClasses, passedClasses, failedClasses, message);

        // when
        final boolean failed = result.failed();

        // then
        assertThat(failed).isTrue();
    }

    @Test
    public void shouldReturnFalseIfResultIsNotFailed() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses =  Lists.newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses = new ArrayList<>();
        final String message = "";
        final Result result = new Result(testedClasses, passedClasses, failedClasses, message);

        // when
        final boolean failed = result.failed();

        // then
        assertThat(failed).isFalse();
    }

    @Test
    @Parameters(method = "objectsForMessageTest")
    public void shouldReturnExpectedMessage(final Result result, final String expectedMessage) {
        // given

        // when
        final String resultMessage = result.getMessage();

        // then
        assertThat(resultMessage).isEqualTo(expectedMessage);
    }

    private Object objectsForMessageTest() {
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses =  Lists.newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses =  Lists.newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> emptyList = Lists.newArrayList();

        final Result result1 = new Result(testedClasses, passedClasses, emptyList, "");
        final String message1 = "Classes that were tested:\n" +
                                "java.lang.Object\n" +
                                "\n" +
                                "Passed tests:\n" +
                                "java.lang.Object \t equals - testName [passed]";

        final Result result2 = new Result(testedClasses, emptyList, failedClasses, "");
        final String message2 = "Classes that were tested:\n" +
                                "java.lang.Object\n" +
                                "\n" +
                                "Failed tests:\n" +
                                "java.lang.Object \t equals - testName [failed]";

        final Result result3 = new Result(testedClasses, passedClasses, failedClasses, "");
        final String message3 = "Classes that were tested:\n" +
                                "java.lang.Object\n" +
                                "\n" +
                                "Passed tests:\n" +
                                "java.lang.Object \t equals - testName [passed]\n" +
                                "\n" +
                                "Failed tests:\n" +
                                "java.lang.Object \t equals - testName [failed]";

        final Result result4 = new Result(testedClasses, passedClasses, failedClasses, "message");
        final String message4 = "Classes that were tested:\n" +
                                "java.lang.Object\n" +
                                "\n" +
                                "Passed tests:\n" +
                                "java.lang.Object \t equals - testName [passed]\n" +
                                "\n" +
                                "Failed tests:\n" +
                                "java.lang.Object \t equals - testName [failed]\n" +
                                "\n" +
                                "What went wrong:\n" +
                                "message";
        return new Object[][]{{result1, message1},
                              {result2, message2},
                              {result3, message3},
                              {result4, message4}};
    }
}