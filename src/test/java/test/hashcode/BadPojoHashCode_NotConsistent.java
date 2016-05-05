package test.hashcode;


public class BadPojoHashCode_NotConsistent {

    private static int counter = 0;

    @Override
    public int hashCode() {
        BadPojoHashCode_NotConsistent.counter++;
        return BadPojoHashCode_NotConsistent.counter % 2;
    }
}
