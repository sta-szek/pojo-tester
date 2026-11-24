package pl.pojo.tester.internal.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import classesForTest.permutator.A;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.pojo.tester.internal.utils.CollectionUtils.asList;

class ThoroughFieldPermutatorTest {

    @Test
    void Should_Create_Permutations() throws NoSuchFieldException {
        // given
        final ThoroughFieldPermutator permutator = new ThoroughFieldPermutator();
        final Field aField = A.class.getDeclaredField("a");
        final Field bField = A.class.getDeclaredField("b");
        final List<Field> elements = asList(aField, bField);
        final List<List<Field>> expectedResult = asList(asList(aField), asList(bField), asList(aField, bField));

        // when
        final List<List<Field>> result = permutator.permute(elements);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void Should_Keep_The_Power_Set_At_The_Limit() {
        final List<Field> fields = instanceFields(Twelve.class);
        final ThoroughFieldPermutator permutator = new ThoroughFieldPermutator();

        assertThat(fields).hasSize(ThoroughFieldPermutator.MAX_FIELDS);
        assertThat(permutator.permute(fields)).isEqualTo(FieldUtils.permutations(fields))
                                               .hasSize((1 << ThoroughFieldPermutator.MAX_FIELDS) - 1);
    }

    @Test
    void Should_Use_The_Linear_Strategy_Above_The_Limit_And_Warn() {
        final List<Field> fields = instanceFields(Thirteen.class);
        final Logger logger = (Logger) LoggerFactory.getLogger(ThoroughFieldPermutator.class);
        final ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);
        logger.setLevel(Level.WARN);
        try {
            final List<List<Field>> result = new ThoroughFieldPermutator().permute(fields);

            assertThat(fields).hasSize(ThoroughFieldPermutator.MAX_FIELDS + 1);
            assertThat(result).isEqualTo(new SublistFieldPermutator().permute(fields)).hasSize(fields.size());
            assertThat(appender.list).anySatisfy(event -> {
                assertThat(event.getLevel()).isEqualTo(Level.WARN);
                assertThat(event.getFormattedMessage()).contains(Thirteen.class.getName())
                                                       .contains(fields.size() + " fields");
            });
        } finally {
            logger.detachAppender(appender);
        }
    }

    private static List<Field> instanceFields(final Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                     .filter(field -> !Modifier.isStatic(field.getModifiers()))
                     .filter(field -> !field.isSynthetic())
                     .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    private static class Twelve {
        private int f01;
        private int f02;
        private int f03;
        private int f04;
        private int f05;
        private int f06;
        private int f07;
        private int f08;
        private int f09;
        private int f10;
        private int f11;
        private int f12;
    }

    @SuppressWarnings("unused")
    private static class Thirteen {
        private int f01;
        private int f02;
        private int f03;
        private int f04;
        private int f05;
        private int f06;
        private int f07;
        private int f08;
        private int f09;
        private int f10;
        private int f11;
        private int f12;
        private int f13;
    }
}
