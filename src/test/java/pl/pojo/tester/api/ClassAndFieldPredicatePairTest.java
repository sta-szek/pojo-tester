package pl.pojo.tester.api;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class ClassAndFieldPredicatePairTest {

    @Test
    public void Should_Create_Predicate_That_Accepts_All_Fields() {
        // given
        final Class<?> clazz = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};
        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(clazz);

        // then
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
    }

    @Test
    public void Should_Create_Predicate_That_Accepts_All_Fields_By_Class_Name() {
        // given
        final Class<?> expectedClass = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};

        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(expectedClass.getName());

        // then
        assertThat(result.getClazz()).isEqualTo(expectedClass);
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
    }

    @Test
    public void Should_Create_Predicate_That_Accepts_Given_Fields_By_Class_Name() {
        // given
        final Class<?> expectedClass = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};

        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(expectedClass.getName(), FieldPredicate.includeAllFields(expectedClass));

        // then
        assertThat(result.getClazz()).isEqualTo(expectedClass);
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
    }
}
