package pl.pojo.tester.usecase.boxed;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Location {

    private Double latitude;
    private Integer rank;
    private Long population;
    private Boolean active;
    private Reading reading;
}
