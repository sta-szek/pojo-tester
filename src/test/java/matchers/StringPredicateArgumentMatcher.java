package matchers;

import java.util.function.Predicate;
import org.mockito.ArgumentMatcher;

public class StringPredicateArgumentMatcher implements ArgumentMatcher<Predicate<String>> {


    @Override
    public boolean matches(final Predicate<String> argument) {
        return argument.test("a");
    }
}
