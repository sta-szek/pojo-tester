package pl.pojo.tester.api;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class NamedPredicate<T> implements Predicate<T> {
    private static final Predicate<String> IS_NOT_NEGATED;
    private static final Predicate<String> IS_NEGATED_AND_NOT_SINGLE;

    static {
        final Predicate<String> startsWithNegationAndBracket = name -> name.startsWith("!(");
        final Predicate<String> endsWithBracket = name -> name.endsWith(")");
        final Predicate<String> isNotSingle = name -> name.contains(",");
        IS_NOT_NEGATED = startsWithNegationAndBracket.negate().and(endsWithBracket.negate());
        IS_NEGATED_AND_NOT_SINGLE = IS_NOT_NEGATED.negate().and(isNotSingle);
    }

    private final String name;
    private final Predicate<T> predicate;

    public NamedPredicate(final String name, final Predicate<T> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    public NamedPredicate(final Predicate<T> predicate) {
        this("", predicate);
    }

    @Override
    public boolean test(final T t) {
        return predicate.test(t);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public NamedPredicate<T> or(final Predicate<? super T> otherPredicate) {
        String otherName = "";
        if (otherPredicate instanceof NamedPredicate) {
            otherName = ((NamedPredicate) otherPredicate).getName();
        }
        return new NamedPredicate<>(skipFirstIfAlwaysTrueOrFalse() + otherName, this.predicate.or(otherPredicate));
    }

    @Override
    public NamedPredicate<T> and(final Predicate<? super T> otherPredicate) {
        String otherName = "";
        if (otherPredicate instanceof NamedPredicate) {
            otherName = ((NamedPredicate) otherPredicate).getName();
        }
        return new NamedPredicate<>(skipFirstIfAlwaysTrueOrFalse() + otherName, this.predicate.and(otherPredicate));
    }

    @Override
    public NamedPredicate<T> negate() {
        final String newName;
        if (IS_NEGATED_AND_NOT_SINGLE.test(name) || IS_NOT_NEGATED.test(name)) {
            newName = "!(" + name + ")";
        } else {
            newName = name.substring(2, name.length() - 1);
        }
        return new NamedPredicate<>(newName, predicate.negate());
    }

    public String getName() {
        return name;
    }

    private String skipFirstIfAlwaysTrueOrFalse() {
        if (StringUtils.isNotBlank(name)) {
            return name + ",";
        } else {
            return "";
        }
    }
}