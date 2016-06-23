package org.pojo.tester.assertion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

public class ResultTest {

    @Test
    public void Should_Return_True_If_Result_Is_Failed() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = new ArrayList<>();
        final List<TestPair> failedClasses = newArrayList(new TestPair("testName", Object.class));
        final String message = "";
        final Result result = new Result(testedClasses, passedClasses, failedClasses, message);

        // when
        final boolean failed = result.failed();

        // then
        assertThat(failed).isTrue();
    }

    @Test
    public void Should_Return_False_If_Result_Is_Not_Failed() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses = new ArrayList<>();
        final String message = "";
        final Result result = new Result(testedClasses, passedClasses, failedClasses, message);

        // when
        final boolean failed = result.failed();

        // then
        assertThat(failed).isFalse();
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Message() throws NoSuchFieldException {
        return objectsForMessageTest().stream()
                                      .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Message(value)));
    }

    private Executable Should_Return_Expected_Message(final TestCase testCase) {
        return () -> {
            // when
            final String resultMessage = testCase.result.getMessage();

            // then
            assertThat(resultMessage).isEqualTo(testCase.expectedResult);
        };
    }

    private List<TestCase> objectsForMessageTest() {
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses = newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> emptyList = newArrayList();

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
        return newArrayList(new TestCase(result1, message1),
                            new TestCase(result2, message2),
                            new TestCase(result3, message3),
                            new TestCase(result4, message4));
    }

    @AllArgsConstructor
    private class TestCase {
        private Result result;
        private String expectedResult;
    }
}
