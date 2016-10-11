package helpers;

import org.mockito.ArgumentMatcher;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import static org.assertj.core.api.Assertions.assertThat;

public class RecursivelyEqualArgumentMatcher implements ArgumentMatcher<ClassAndFieldPredicatePair> {
    private final ClassAndFieldPredicatePair expectedParameter;

    public RecursivelyEqualArgumentMatcher(final ClassAndFieldPredicatePair expectedParameter) {
        this.expectedParameter = expectedParameter;
    }

    @Override
    public boolean matches(final ClassAndFieldPredicatePair argument) {
        assertThat(argument).isEqualToComparingFieldByFieldRecursively(expectedParameter);
        return true;
    }
}
