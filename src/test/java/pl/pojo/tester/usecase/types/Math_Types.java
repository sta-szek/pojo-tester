package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Math_Types {

    private BigInteger bigInteger;
    private BigDecimal bigDecimal;
}
