package classesForTest.equals;


public class BadPojoEquals_NotTransitive_A_B {

    private static int counter = -1;

    @Override
    public boolean equals(final Object obj) {
        BadPojoEquals_NotTransitive_A_B.counter++;
        return BadPojoEquals_NotTransitive_A_B.counter % 3 == 0;
    }
}
