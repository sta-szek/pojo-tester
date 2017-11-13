package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class JavaTypeInstantiator extends AbstractObjectInstantiator {

    private final Map<String, Object> preparedObjects = new HashMap<>();

    {
        preparedObjects.put("boolean", Boolean.TRUE);
        preparedObjects.put("byte", (byte) -1);
        preparedObjects.put("char", 'b');
        preparedObjects.put("double", -2.5D);
        preparedObjects.put("float", -3.5F);
        preparedObjects.put("int", -4);
        preparedObjects.put("long", -5L);
        preparedObjects.put("short", (short) -6);
        preparedObjects.put(Boolean.class.getName(), Boolean.FALSE);
        preparedObjects.put(Byte.class.getName(), (byte) 1);
        preparedObjects.put(Character.class.getName(), 'a');
        preparedObjects.put(Double.class.getName(), 2.5D);
        preparedObjects.put(Float.class.getName(), 3.5F);
        preparedObjects.put(Integer.class.getName(), 4);
        preparedObjects.put(Long.class.getName(), 5L);
        preparedObjects.put(Short.class.getName(), (short) 6);

        preparedObjects.put(Class.class.getName(), Object.class);
        preparedObjects.put(String.class.getName(), "www.pojo.pl");
        preparedObjects.put(UUID.class.getName(), UUID.randomUUID());

        preparedObjects.put(BigDecimal.class.getName(), BigDecimal.ONE);
        preparedObjects.put(BigInteger.class.getName(), BigInteger.ONE);

        preparedObjects.put(java.sql.Date.class.getName(), java.sql.Date.valueOf(LocalDate.now()));
        preparedObjects.put(Date.class.getName(), Date.from(Instant.now()));


        preparedObjects.put(Clock.class.getName(), Clock.systemDefaultZone());
        preparedObjects.put(Duration.class.getName(), Duration.ZERO);
        preparedObjects.put(Instant.class.getName(), Instant.now());
        preparedObjects.put(LocalDate.class.getName(), LocalDate.now());
        preparedObjects.put(LocalDateTime.class.getName(), LocalDateTime.now());
        preparedObjects.put(LocalTime.class.getName(), LocalTime.now());
        preparedObjects.put(MonthDay.class.getName(), MonthDay.now());
        preparedObjects.put(OffsetDateTime.class.getName(), OffsetDateTime.now());
        preparedObjects.put(OffsetTime.class.getName(), OffsetTime.now());
        preparedObjects.put(Period.class.getName(), Period.ZERO);
        preparedObjects.put(Year.class.getName(), Year.now());
        preparedObjects.put(YearMonth.class.getName(), YearMonth.now());
        preparedObjects.put(ZonedDateTime.class.getName(), ZonedDateTime.now());
        preparedObjects.put(ZoneId.class.getName(), ZoneId.systemDefault());
        preparedObjects.put(ZoneOffset.class.getName(), ZoneOffset.UTC);
    }

    JavaTypeInstantiator(final Class<?> clazz,
                         final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        final String canonicalName = clazz.getCanonicalName();
        return preparedObjects.get(canonicalName);
    }

    @Override
    public boolean canInstantiate() {
        return clazz.isPrimitive() || preparedObjects.containsKey(clazz.getName());
    }
}
