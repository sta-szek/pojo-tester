package org.pojo.tester.assertion;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultBuilder {
    private final StringBuilder cumulativeMessage = new StringBuilder();
    private final List<Class> failedClasses = new ArrayList<>();
    private final List<Class> passedClasses = new ArrayList<>();
    private final Set<Class> testedClasses = new HashSet<>();

    public void fail(final String message, final Object... objects) {
        cumulativeMessage.append(message);
        final Class<?> failedClass = objects[0].getClass();
        failedClasses.add(failedClass);
        testedClasses.add(failedClass);
    }

    public void pass(final Object... objects) {
        final Class<?> failedClass = objects[0].getClass();
        passedClasses.add(failedClass);
        testedClasses.add(failedClass);
    }

    public Result build() {
        return new Result(testedClasses, passedClasses, failedClasses, cumulativeMessage.toString());
    }
}
