package org.pojo.tester;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.GoodPojo_Equals_HashCode_ToString;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class ClassAndFieldPredicatePairTest {

    @Test
    public void Should_Create_Predicate_That_Accepts_All_Fields() {
        // given
        final Class<GoodPojo_Equals_HashCode_ToString> clazz = GoodPojo_Equals_HashCode_ToString.class;
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
}
