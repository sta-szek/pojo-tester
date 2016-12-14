package pl.pojo.tester.api;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


public class ConstructorParametersTest {

    @Test
    public void Should_Equal_Itself() {
        // given
        final ConstructorParameters constructorParameters = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final boolean result = constructorParameters.equals(constructorParameters);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Equal_Other_Object_With_Same_Values() {
        // given
        final ConstructorParameters constructorParameters1 = new ConstructorParameters(new Object[0], new Class[0]);
        final ConstructorParameters constructorParameters2 = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final boolean result = constructorParameters1.equals(constructorParameters2);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Not_Equal_Null() {
        // given
        final ConstructorParameters constructorParameters = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final boolean result = constructorParameters.equals(null);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Not_Equal_Other_Object_With_Different_Values() {
        // given
        final ConstructorParameters constructorParameters1 = new ConstructorParameters(new Object[0], new Class[0]);
        final ConstructorParameters constructorParameters2 = new ConstructorParameters(new Object[1], new Class[1]);

        // when
        final boolean result = constructorParameters1.equals(constructorParameters2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Not_Equal_Other_Class() {
        // given
        final ConstructorParameters constructorParameters1 = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final boolean result = constructorParameters1.equals(String.class);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Generate_Same_Hash_Codes() {
        // given
        final ConstructorParameters constructorParameters1 = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final int result1 = constructorParameters1.hashCode();
        final int result2 = constructorParameters1.hashCode();

        // then
        assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void Should_Generate_Same_Hash_Codes_For_Equal_Objects() {
        // given
        final ConstructorParameters constructorParameters1 = new ConstructorParameters(new Object[0], new Class[0]);
        final ConstructorParameters constructorParameters2 = new ConstructorParameters(new Object[0], new Class[0]);

        // when
        final int result1 = constructorParameters1.hashCode();
        final int result2 = constructorParameters2.hashCode();

        // then
        assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void Should_Return_True_If_Contains_Matching_Parameter_Types() {
        // given
        final Class[] expectedToContain = {String.class};
        final ConstructorParameters constructorParameters = new ConstructorParameters(new Object[0], expectedToContain);

        // when
        final boolean result = constructorParameters.matches(expectedToContain);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_False_If_Does_Not_Contain_Matching_Parameter_Types() {
        // given
        final Class[] constructorParametersTypes = {String.class};
        final Class[] expectedNotToContain = {Object.class};
        final ConstructorParameters constructorParameters = new ConstructorParameters(new Object[0], constructorParametersTypes);

        // when
        final boolean result = constructorParameters.matches(expectedNotToContain);

        // then
        assertThat(result).isFalse();
    }

}
