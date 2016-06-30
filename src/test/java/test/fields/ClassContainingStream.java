package test.fields;


import java.util.stream.Stream;
import test.A;

public class ClassContainingStream {

    private final Stream<Integer> stream_Integer = Stream.of(1);
    private Stream<String> stream_String;
    private Stream<Object> stream_Object;
    private Stream<A> stream_A;
    private Stream stream;
    private A a;
}
