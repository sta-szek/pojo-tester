package test.equals;


public class BadPojoEquals_NotConsistent {

    private static int counter = -1;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            BadPojoEquals_NotConsistent.counter++;
        }
        return BadPojoEquals_NotConsistent.counter % 2 == 0;
    }
}
