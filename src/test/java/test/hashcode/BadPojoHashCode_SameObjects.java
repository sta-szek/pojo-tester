package test.hashcode;


public class BadPojoHashCode_SameObjects {

    private static int counter = 0;

    @Override
    public int hashCode() {
        BadPojoHashCode_SameObjects.counter++;
        return BadPojoHashCode_SameObjects.counter;
    }
}
