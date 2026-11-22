package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.utils.ThoroughFieldPermutator;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SupplierInstantiatorTest {

    @Test
    void Should_Use_Supplier_Instead_Of_Built_In_Instantiator() {
        final ObjectGenerator generator = new ObjectGenerator(DefaultFieldValueChanger.INSTANCE,
                                                              new ArrayListValuedHashMap<>(),
                                                              new ThoroughFieldPermutator(),
                                                              Collections.singletonMap(String.class, () -> new String("accepted")));

        final Object first = generator.createNewInstance(String.class);
        final Object second = generator.createNewInstance(String.class);

        assertThat(first).isEqualTo("accepted");
        assertThat(second).isEqualTo("accepted");
        assertThat(first).isNotSameAs(second);
    }

    @Test
    void Should_Fail_When_Supplier_Returns_Null() {
        final ObjectGenerator generator = new ObjectGenerator(DefaultFieldValueChanger.INSTANCE,
                                                              new ArrayListValuedHashMap<>(),
                                                              new ThoroughFieldPermutator(),
                                                              Collections.singletonMap(String.class, () -> null));

        final Throwable result = catchThrowable(() -> generator.createNewInstance(String.class));

        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }
}
