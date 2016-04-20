package org.pojo.tester.assertion;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultBuilder {
    private final StringBuilder cumulativeMessage = new StringBuilder();
    private final List<TestPair> failedClasses = new ArrayList<>();
    private final List<TestPair> passedClasses = new ArrayList<>();
    private final Set<Class> testedClasses = new HashSet<>();

    public void fail(final Class<?> failedClass, final String testName, final String errorMessage) {
        cumulativeMessage.append(errorMessage)
                         .append("\n");
        failedClasses.add(new TestPair(testName, failedClass));
        testedClasses.add(failedClass);
    }

    public void pass(final Class<?> passedClass, final String testName) {
        passedClasses.add(new TestPair(testName, passedClass));
        testedClasses.add(passedClass);
    }

    public Result build() {
        return new Result(testedClasses, passedClasses, failedClasses, cumulativeMessage.toString());
    }
}
