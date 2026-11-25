package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.utils.ThoroughFieldPermutator;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class JdkFieldWalkTest {

    @Test
    void Should_Not_Copy_Fields_Declared_On_Java_Types() {
        final ObjectGenerator generator = new ObjectGenerator(DefaultFieldValueChanger.INSTANCE,
                                                              new ArrayListValuedHashMap<>(),
                                                              new ThoroughFieldPermutator(),
                                                              false);

        assertThat(generator.getAllFields(new UnknownIdException("abc"))
                            .stream()
                            .map(Field::getName)
                            .collect(Collectors.toList()))
                .contains("code")
                .doesNotContain("backtrace", "detailMessage", "cause");
    }

    private static final class UnknownIdException extends RuntimeException {
        private String code;

        private UnknownIdException(final String code) {
            super(code);
            this.code = code;
        }
    }
}
