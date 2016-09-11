package matchers;

import org.mockito.ArgumentMatcher;
import pl.pojo.tester.ClassAndFieldPredicatePair;

public class ClassAndFieldPredicatePairArgumentMatcher extends ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final Class<?> clazz;
    private final String fieldName;

    public ClassAndFieldPredicatePairArgumentMatcher(final Class<?> clazz, final String fieldName) {
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    @Override
    public boolean matches(final Object argument) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = (ClassAndFieldPredicatePair) argument;

        final boolean classesMatches = classAndFieldPredicatePair.getClazz()
                                                                 .equals(clazz);

        final boolean predicateMatches = classAndFieldPredicatePair.getFieldsPredicate()
                                                                   .test(fieldName);
        return classesMatches && predicateMatches;
    }
}
