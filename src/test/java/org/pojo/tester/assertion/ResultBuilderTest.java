package org.pojo.tester.assertion;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ResultBuilderTest {

    @Test
    public void shouldBuildEmptyResult() {
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
    public void shouldBuildResultWithPassAndTestedClass() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = Lists.newArrayList(new TestPair("testName", Object.class));
        final List<TestPair> failedClasses = new ArrayList<>();
        final String message = "";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);
        String testName = "testName";

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.pass(Object.class, testName);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult);
    }

    @Test
    public void shouldBuildResultWithFailAndTestedClassAndMessage() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final List<TestPair> passedClasses = new ArrayList<>();
        final List<TestPair> failedClasses = Lists.newArrayList(new TestPair("testName",Object.class));
        final String message = "message";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);
        String testName = "testName";

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.fail(Object.class, testName, message);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expectedResult);
    }
}