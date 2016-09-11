package matchers;

import org.mockito.ArgumentMatcher;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import static org.assertj.core.api.Assertions.assertThat;

public class RecursivelyEqualArgumentMatcher extends ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final ClassAndFieldPredicatePair expectedParameter;

    public RecursivelyEqualArgumentMatcher(final ClassAndFieldPredicatePair expectedParameter) {
        this.expectedParameter = expectedParameter;
    }

    @Override
    public boolean matches(final Object argument) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = (ClassAndFieldPredicatePair) argument;
        assertThat(classAndFieldPredicatePair).isEqualToComparingFieldByFieldRecursively(expectedParameter);
        return true;
    }
}
