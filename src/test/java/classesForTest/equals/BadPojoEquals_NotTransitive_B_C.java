package classesForTest.equals;


public class BadPojoEquals_NotTransitive_B_C {

    private static int counter = 0;

    @Override
    public boolean equals(final Object obj) {
        BadPojoEquals_NotTransitive_B_C.counter++;
        return BadPojoEquals_NotTransitive_B_C.counter % 3 == 0;
    }
}
