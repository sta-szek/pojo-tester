package matchers;

import java.util.function.Predicate;
import org.mockito.ArgumentMatcher;

public class StringPredicateArgumentMatcher extends ArgumentMatcher<Predicate<String>> {

    @Override
    public boolean matches(final Object argument) {
        final Predicate<String> stringPredicate = (Predicate<String>) argument;
        return stringPredicate.test("a");
    }
}
