package pl.pojo.tester.internal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ThoroughFieldPermutator implements Permutator {

    /**
     * Largest number of fields still combined as a power set. A class with more fields uses the
     * linear strategy, the same one as {@code quickly()}.
     */
    public static final int MAX_FIELDS = 12;

    private static final Logger LOGGER = LoggerFactory.getLogger(ThoroughFieldPermutator.class);
    private static final Set<String> REPORTED = ConcurrentHashMap.newKeySet();

    @Override
    public List<List<Field>> permute(final List<Field> elements) {
        if (elements.size() > MAX_FIELDS) {
            report(elements);
            return new SublistFieldPermutator().permute(elements);
        }
        return FieldUtils.permutations(elements);
    }

    private static void report(final List<Field> elements) {
        final String type = elements.stream()
                                    .map(field -> field.getDeclaringClass().getName())
                                    .distinct()
                                    .collect(Collectors.joining(", "));
        if (REPORTED.add(type)) {
            LOGGER.warn("{} has {} fields. Thorough generation combines at most {} and uses the linear strategy for this class.",
                        type,
                        elements.size(),
                        MAX_FIELDS);
        }
    }
}
