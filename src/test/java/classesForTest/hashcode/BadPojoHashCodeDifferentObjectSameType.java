package classesForTest.hashcode;


public class BadPojoHashCodeDifferentObjectSameType {

    private static int increment;

    @Override
    public int hashCode() {
        return BadPojoHashCodeDifferentObjectSameType.increment++;
    }
}
