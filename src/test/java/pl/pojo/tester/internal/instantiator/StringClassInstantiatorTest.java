package pl.pojo.tester.internal.instantiator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class StringClassInstantiatorTest {

    @Test
    public void Should_Return_Pojo_String() {
        // given
        final StringClassInstantiator instantiator = new StringClassInstantiator();
        final String expectedResult = "www.pojo.pl";

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }
}
