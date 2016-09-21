package matchers;

import org.mockito.ArgumentMatcher;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

public class ClassNameAndFieldPredicatePairArgumentMatcher extends ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final String className;
    private final String fieldName;

    public ClassNameAndFieldPredicatePairArgumentMatcher(final String className, final String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    @Override
    public boolean matches(final Object argument) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = (ClassAndFieldPredicatePair) argument;

        final boolean classesMatches = classAndFieldPredicatePair.getClazz()
                                                                 .getName()
                                                                 .equals(className);

        final boolean predicateMatches = classAndFieldPredicatePair.getFieldsPredicate()
                                                                   .test(fieldName);
        return classesMatches && predicateMatches;
    }
}
