package org.pojo.tester.assertion;


import java.util.List;
import java.util.Set;

class Result {
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

    public Set<Class> getTestedClasses() {
        return testedClasses;
    }

    public List<Class> getPassedClasses() {
        return passedClasses;
    }

    public List<Class> getFailedClasses() {
        return failedClasses;
    }

    public String getMessage() {
        return message;
    }

    public boolean passed() {
        return failedClasses.size() == 0;
    }

    public boolean failed() {
        return failedClasses.size() != 0;
    }

    public int getPassedSize() {
        return passedClasses.size();
    }

    public int getFailedSize() {
        return failedClasses.size();
    }

}
