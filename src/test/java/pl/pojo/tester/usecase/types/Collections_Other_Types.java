package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collections_Other_Types {

    private boolean[] boolean_;
    private byte[] byte_;
    private char[] character_;
    private double[] double_;
    private int[] integer_;
    private long[] long_;
    private short[] short_;
    private float[] float_;
    private Stream<Object> stream;
}
