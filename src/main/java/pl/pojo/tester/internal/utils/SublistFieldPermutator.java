package pl.pojo.tester.internal.utils;

import java.lang.reflect.Field;
import java.util.List;

public class SublistFieldPermutator implements Permutator {

    @Override
    public List<List<Field>> permute(final List<Field> elements) {
        return Sublists.subsequences(elements);
    }
}
