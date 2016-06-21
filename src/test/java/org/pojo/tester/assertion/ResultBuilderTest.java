package org.pojo.tester.assertion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ResultBuilderTest {

    @Test
    public void Should_Build_Empty_Result() {
        // given
        final HashSet<Class> testedClasses = new HashSet<>();
        final List<TestPair> passedClasses = new ArrayList<>();
        final List<TestPair> failedClasses = new ArrayList<>();
        final String message = "";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Build_Result_With_Pass_And_Tested_Class() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = Lists.newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses = new ArrayList<>();
        final String message = "";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);
        final String testName = "testName";

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.pass(Object.class, testName);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult);
    }

    @Test
    public void Should_Build_Result_With_Fail_And_Tested_Class_And_Message() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = new ArrayList<>();
        final List<TestPair> failedClasses = Lists.newArrayList(new TestPair("testName", Object.class));
        final String message = "message";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message + "\n");
        final String testName = "testName";

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.fail(Object.class, testName, message);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult);
    }
}
