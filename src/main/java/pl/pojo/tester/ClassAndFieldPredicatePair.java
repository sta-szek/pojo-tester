package pl.pojo.tester;

import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClassAndFieldPredicatePair {
    private final Class clazz;
    private final Predicate<String> fieldsPredicate;

    public ClassAndFieldPredicatePair(final Class clazz) {
        this(clazz, FieldPredicate.includeAllFields(clazz));
    }
}
