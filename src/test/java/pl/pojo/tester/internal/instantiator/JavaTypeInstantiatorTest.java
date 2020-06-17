package pl.pojo.tester.internal.instantiator;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class JavaTypeInstantiatorTest {

    @TestFactory
    Stream<DynamicTest> Should_Instantiate_Primitive() {
        return Stream.of(Integer.class,
                         Byte.class,
                         Character.class,
                         Double.class,
                         Float.class,
                         Integer.class,
                         Long.class,
                         Short.class,
                         boolean.class,
                         byte.class,
                         char.class,
                         double.class,
                         float.class,
                         int.class,
                         long.class,
                         short.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Instantiate_Primitive(value)));
    }

    private Executable Should_Instantiate_Primitive(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final JavaTypeInstantiator instantiator = new JavaTypeInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isNotNull();
        };
    }

    @TestFactory
    Stream<DynamicTest> Should_Instantiate_Java_Type_Classes() {
        return Stream.of(Boolean.class,
                         Byte.class,
                         Character.class,
                         Double.class,
                         Float.class,
                         Integer.class,
                         Long.class,
                         Short.class,
                         Class.class,
                         String.class,
                         UUID.class,
                         BigDecimal.class,
                         BigInteger.class,
                         java.sql.Date.class,
                         Date.class,
                         Clock.class,
                         Duration.class,
                         Instant.class,
                         LocalDate.class,
                         LocalDateTime.class,
                         LocalTime.class,
                         MonthDay.class,
                         OffsetDateTime.class,
                         OffsetTime.class,
                         Period.class,
                         Year.class,
                         YearMonth.class,
                         ZonedDateTime.class,
                         ZoneId.class,
                         ZoneOffset.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Instantiate_Java_Type_Classes(value)));
    }

    private Executable Should_Instantiate_Java_Type_Classes(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final JavaTypeInstantiator instantiator = new JavaTypeInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate)
                              .isNotNull();
        };
    }
}
