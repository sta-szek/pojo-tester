package pl.pojo.tester.internal.field;

import classesForTest.fields.AllFiledTypes;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

public class UUIDValueChangerTest {

    private final AbstractFieldValueChanger<UUID> valueChanger = new UUIDValueChanger();

    @Test
    public void Should_Change_Value() {
        // given
        UUID uuid = UUID.randomUUID();
        final AllFiledTypes helpClass1 = new AllFiledTypes(uuid);
        final AllFiledTypes helpClass2 = new AllFiledTypes(uuid);

        // when
        valueChanger.changeFieldsValues(helpClass1,
                                        helpClass2,
                                        Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final UUID result1 = getInternalState(helpClass1, "uuid");
        final UUID result2 = getInternalState(helpClass2, "uuid");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        UUID uuid = UUID.randomUUID();
        return Stream.of(new TestCase(null, null, false),
                         new TestCase(uuid, uuid, false),
                         new TestCase(uuid, null, true),
                         new TestCase(null, uuid, true),
                         new TestCase(uuid, UUID.randomUUID(), true))
                .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                          Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @AllArgsConstructor
    private class TestCase {
        private UUID value1;
        private UUID value2;
        private boolean result;
    }
}