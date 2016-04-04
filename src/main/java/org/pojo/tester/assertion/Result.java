package org.pojo.tester.assertion;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

class Result {
    private static final String NEW_LINE = "\n";
    private static final String DOUBLE_NEW_LINE = NEW_LINE + NEW_LINE;
    private static final String failTestMessage = "%s %t";

    private final Set<Class> testedClasses;
    private final List<TestPair> passedClasses;
    private final List<TestPair> failedClasses;
    private final String message;

    Result(final Set<Class> testedClasses, final List<TestPair> passedClasses, final List<TestPair> failedClasses, final String message) {
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
            testedClasses.forEach(failedClass -> stringBuilder.append(failedClass)
                                                              .append(" ")
                                                              .append(NEW_LINE));
        }
    }

    private void appendPassedClasses(final StringBuilder stringBuilder) {
        if (!passedClasses.isEmpty()) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Classes that passed all tests:")
                         .append(NEW_LINE);
            iterateOverAndAppendCanonicalClassName(passedClasses, stringBuilder, "passed");
        }
    }

    private void appendFailedClasses(final StringBuilder stringBuilder) {
        if (!failedClasses.isEmpty()) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Classes that failed tests:")
                         .append(NEW_LINE);
            iterateOverAndAppendCanonicalClassName(failedClasses, stringBuilder, "failed");
        }
    }

    private void iterateOverAndAppendCanonicalClassName(final Collection<TestPair> collection,
                                                        final StringBuilder stringBuilder,
                                                        final String additionalMessage) {
        collection.stream()
                  .map(this::formatMessage)
                  .forEach(failedClass -> stringBuilder.append(failedClass)
                                                       .append(" ")
                                                       .append(additionalMessage)
                                                       .append(NEW_LINE));
    }

    private String formatMessage(final TestPair testPair) {
        final String canonicalName = testPair.getTestClass()
                                             .getCanonicalName();
        final String testName = testPair.getTestName();
        return canonicalName + " " + testName;
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
