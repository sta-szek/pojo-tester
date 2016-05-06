package test.hashcode;


public class BadPojoHashCodeItself {

    private int increment;

    @Override
    public int hashCode() {
        return increment++;
    }
}
