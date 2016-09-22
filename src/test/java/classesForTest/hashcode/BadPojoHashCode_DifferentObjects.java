package classesForTest.hashcode;


public class BadPojoHashCode_DifferentObjects {

    int a;

    public BadPojoHashCode_DifferentObjects(final int a) {
        this.a = a;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
