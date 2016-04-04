package org.pojo.tester.assertion;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


public class ResultBuilderTest {

    @Test
    public void shouldBuildEmptyResult() {
        // given
        final HashSet<Class> testedClasses = new HashSet<>();
        final ArrayList<Class> passedClasses = new ArrayList<>();
        final ArrayList<Class> failedClasses = new ArrayList<>();
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
        final ArrayList<Class> passedClasses = Lists.newArrayList(Object.class);
        final ArrayList<Class> failedClasses = new ArrayList<>();
        final String message = "";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.pass(Object.class);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void shouldBuildResultWithFailAndTestedClassAndMessage() {
        // given
        final HashSet<Class> testedClasses = Sets.newLinkedHashSet(Object.class);
        final ArrayList<Class> passedClasses = new ArrayList<>();
        final ArrayList<Class> failedClasses = Lists.newArrayList(Object.class);
        final String message = "message";
        final Result expectedResult = new Result(testedClasses, passedClasses, failedClasses, message);

        final ResultBuilder resultBuilder = new ResultBuilder();

        // when
        resultBuilder.fail(message,Object.class);
        final Result result = resultBuilder.build();

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }
}