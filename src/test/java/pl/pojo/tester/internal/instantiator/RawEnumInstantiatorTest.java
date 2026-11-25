package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import static org.assertj.core.api.Assertions.assertThat;

class RawEnumInstantiatorTest {

    @Test
    void Should_Not_Call_The_Enum_Constructor() {
        final MultiValuedMap<Class<?>, ConstructorParameters> parameters = new ArrayListValuedHashMap<>();

        final AbstractObjectInstantiator instantiator = Instantiable.forClass(Enum.class, parameters);

        assertThat(instantiator).isInstanceOf(EnumInstantiator.class);
        assertThat(instantiator.instantiate()).isEqualTo(java.math.RoundingMode.UP);
    }
}
