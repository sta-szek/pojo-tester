package test.equals;


public class BadPojoEquals_NotConsistent {

    private final boolean[] equalReturnValues = new boolean[2];
    private int counter = -1;

    public BadPojoEquals_NotConsistent(final boolean firstReturn, final boolean secondReturn) {
        this.equalReturnValues[0] = firstReturn;
        this.equalReturnValues[1] = secondReturn;
    }

    @Override
    public boolean equals(final Object obj) {
        counter++;
        return equalReturnValues[counter];
    }
}
