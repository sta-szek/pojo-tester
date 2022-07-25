package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collections_Types {

    private int[] array;
    private Stream stream;

}
