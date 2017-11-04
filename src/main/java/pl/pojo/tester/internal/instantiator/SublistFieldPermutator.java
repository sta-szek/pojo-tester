package pl.pojo.tester.internal.instantiator;

import pl.pojo.tester.internal.utils.Sublists;

import java.lang.reflect.Field;
import java.util.List;

public class SublistFieldPermutator implements Permutator {

    @Override
    public List<List<Field>> permute(final List<Field> elements) {
        return Sublists.subsequences(elements);
    }
}
