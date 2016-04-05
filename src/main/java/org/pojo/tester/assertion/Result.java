package org.pojo.tester.assertion;


import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Result {
    private static final String NEW_LINE = "\n";
    private static final String DOUBLE_NEW_LINE = NEW_LINE + NEW_LINE;
    private static final String TEST_MESSAGE = "%s \t equals - %s [%s]";
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
            stringBuilder.append("Classes that were tested:")
                         .append(NEW_LINE);
            final String message = testedClasses.stream()
                                                .map(Class::getCanonicalName)
                                                .collect(Collectors.joining(NEW_LINE));

            stringBuilder.append(message);
        }
    }

    private void appendPassedClasses(final StringBuilder stringBuilder) {
        if (!passedClasses.isEmpty()) {
            stringBuilder.append(DOUBLE_NEW_LINE);
            stringBuilder.append("Passed tests:")
                         .append(NEW_LINE);
            iterateOverAndAppendCanonicalClassName(passedClasses, stringBuilder, "passed");
        }
    }

    private void appendFailedClasses(final StringBuilder stringBuilder) {
        if (!failedClasses.isEmpty()) {
            stringBuilder.append(DOUBLE_NEW_LINE);
            stringBuilder.append("Failed tests:")
                         .append(NEW_LINE);
            iterateOverAndAppendCanonicalClassName(failedClasses, stringBuilder, "failed");
        }
    }

    private void iterateOverAndAppendCanonicalClassName(final Collection<TestPair> collection,
                                                        final StringBuilder stringBuilder,
                                                        final String additionalMessage) {
        final String message = collection.stream()
                                         .map(testPair -> formatMessage(testPair, additionalMessage))
                                         .collect(Collectors.joining(NEW_LINE));
        stringBuilder.append(message);
    }

    private String formatMessage(final TestPair testPair, final String additionalMessage) {
        final String canonicalName = testPair.getTestClass()
                                             .getCanonicalName();
        final String testName = testPair.getTestName();
        return String.format(TEST_MESSAGE, canonicalName, testName, additionalMessage);
    }

    private void appendDetailedMessage(final StringBuilder stringBuilder) {
        if (StringUtils.isNotBlank(message)) {
            stringBuilder.append(DOUBLE_NEW_LINE);
            stringBuilder.append("What went wrong:")
                         .append(NEW_LINE)
                         .append(message);
        }
    }

}
