package pl.pojo.tester.internal.instantiator;

import pl.pojo.tester.internal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ThoroughFieldPermutator implements Permutator {

    @Override
    public List<List<Field>> permute(final List<Field> elements) {
        return FieldUtils.permutations(elements);
    }
}
