package pl.pojo.tester.issue192;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@ToString
@EqualsAndHashCode
@Setter
@Getter
class ConstructorWithZonedDateTime {

    private ZonedDateTime zonedDateTime;

    ConstructorWithZonedDateTime(final ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }
}
