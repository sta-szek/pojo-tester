package org.pojo.tester;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TestResult {
    private static final String TEST_RESULT_BEGIN = "Equals test result by pojo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private static final String TEST_RESULT_END = "Equals test result by pojo. <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<";
    private Collection<Class> testedClasses;
    private Collection<Class> classesThatPassedTests;
    private Collection<Class> classesThatFailedTests;
    private Collection<String> messages;

    private TestResult(Builder builder) {
        this.testedClasses = builder.testedCalsses;
        this.classesThatPassedTests = builder.classesThatPassedTests;
        this.classesThatFailedTests = builder.classesThatFailedTests;
        this.messages = builder.messages;
    }

    public Collection<Class> getTestedClasses() {
        return testedClasses;
    }

    public Collection<Class> getClassesThatPassedTests() {
        return classesThatPassedTests;
    }

    public Collection<Class> getClassesThatFailedTests() {
        return classesThatFailedTests;
    }

    public Collection<String> getMessages() {
        return messages;
    }

    public int getPassedSize() {
        return classesThatPassedTests.size();
    }

    public int getFailedSize() {
        return classesThatFailedTests.size();
    }

    public String getFormattedMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TEST_RESULT_BEGIN + "\n");
        messages.stream()
                .forEach(x -> stringBuilder.append(x + "\n"));
        stringBuilder.append(TEST_RESULT_END + "\n");
        return stringBuilder.toString();
    }

    public static class Builder {
        private Collection<Class> testedCalsses;
        private Collection<Class> classesThatPassedTests = new ArrayList<>();
        private Collection<Class> classesThatFailedTests = new ArrayList<>();
        private List<String> messages = new ArrayList<>();

        private Builder(Collection<Class> testedCalsses) {
            this.testedCalsses = testedCalsses;
        }


        public static Builder forClasses(Class... classes) {
            return forClasses(Arrays.stream(classes)
                                    .collect(Collectors.toList()));
        }

        public static Builder forClasses(Collection<Class> classes) {
            return new Builder(classes);
        }

        public Builder pass(Class clazz) {
            classesThatPassedTests.add(clazz);
            return this;
        }

        public Builder fail(Class clazz) {
            classesThatFailedTests.add(clazz);
            return this;
        }

        public Builder message(Class clazz, String message) {
            messages.add(message);
            return this;
        }

        public TestResult build() {
            return new TestResult(this);
        }
    }
}
