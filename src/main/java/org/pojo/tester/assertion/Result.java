package org.pojo.tester.assertion;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

class Result {
    private static final String NEW_LINE = "\n";
    private final Set<Class> testedClasses;
    private final List<Class> passedClasses;
    private final List<Class> failedClasses;
    private final String message;

    Result(final Set<Class> testedClasses, final List<Class> passedClasses, final List<Class> failedClasses, final String message) {
        this.testedClasses = testedClasses;
        this.passedClasses = passedClasses;
        this.failedClasses = failedClasses;
        this.message = message;
    }

    String getMessage() {
        final StringBuilder stringBuilder = new StringBuilder();
        appendTestedClasses(stringBuilder);
        appendPassedClasses(stringBuilder);
        appendFailedClasses(stringBuilder);
        appendDetailedMessage(stringBuilder);
        return stringBuilder.toString();
    }

    boolean failed() {
        return !failedClasses.isEmpty();
    }

    private void appendTestedClasses(final StringBuilder stringBuilder) {
        if (!testedClasses.isEmpty()) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Classes that were tested:")
                         .append(NEW_LINE);
            testedClasses.forEach(testedClass -> stringBuilder.append(testedClass)
                                                              .append(NEW_LINE));
        }
    }

    private void appendPassedClasses(final StringBuilder stringBuilder) {
        if (!passedClasses.isEmpty()) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Classes that passed all tests:")
                         .append(NEW_LINE);
            passedClasses.forEach(passedClass -> stringBuilder.append(passedClass)
                                                              .append(NEW_LINE));
        }
    }

    private void appendFailedClasses(final StringBuilder stringBuilder) {
        if (!failedClasses.isEmpty()) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Classes that failed tests:")
                         .append(NEW_LINE);
            failedClasses.forEach(failedClass -> stringBuilder.append(failedClass)
                                                              .append(NEW_LINE));
        }
    }

    private void appendDetailedMessage(final StringBuilder stringBuilder) {
        if (StringUtils.isNotBlank(message)) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("What went wrong:")
                         .append(NEW_LINE)
                         .append(message);
        }
    }

}
