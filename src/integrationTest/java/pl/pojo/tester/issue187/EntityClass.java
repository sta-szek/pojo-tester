package pl.pojo.tester.issue187;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class EntityClass {

    private String f1;
    private String f2;
    private int f3;
    private Date f4;
    private LocalDate f5;
    private LocalDateTime f6;
    private LocalTime f7;
    private java.sql.Date f8;
    private ZonedDateTime f9;
}
