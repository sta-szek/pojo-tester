package org.pojo.tester.instantiator;

import org.junit.Test;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;
import test.GoodPojo_Equals_HashCode_ToString;
import test.instantiator.NoDefaultConstructor;
import test.instantiator.PackageConstructor;
import test.instantiator.PrivateConstructor;
import test.instantiator.ProtectedConstructor;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectGenerator_NewInstanceTest {

    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger = AbstractPrimitiveValueChanger.getInstance();

    @Test
    public void Should_Create_New_Instance() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Object result = objectGenerator.createNewInstance(GoodPojo_Equals_HashCode_ToString.class);

        // then
        assertThat(result).isInstanceOf(GoodPojo_Equals_HashCode_ToString.class);
    }

    @Test
    public void Should_Create_New_Instance_Without_Default_Constructor() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Object result = objectGenerator.createNewInstance(NoDefaultConstructor.class);

        // then
        assertThat(result).isInstanceOf(NoDefaultConstructor.class);
    }

    @Test
    public void Should_Create_New_Instance_With_Package_Constructor() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Object result = objectGenerator.createNewInstance(PackageConstructor.class);

        // then
        assertThat(result).isInstanceOf(PackageConstructor.class);
    }

    @Test
    public void Should_Create_New_Instance_With_Protected_Constructor() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Object result = objectGenerator.createNewInstance(ProtectedConstructor.class);

        // then
        assertThat(result).isInstanceOf(ProtectedConstructor.class);
    }

    @Test
    public void Should_Create_New_Instance_With_Private_Constructor() {
        // given
        final ObjectGenerator objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);

        // when
        final Object result = objectGenerator.createNewInstance(PrivateConstructor.class);

        // then
        assertThat(result).isInstanceOf(PrivateConstructor.class);
    }

}
