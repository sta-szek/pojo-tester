package classesForTest;

public class Constructors_First_Throws_Exception {
    private Constructors_First_Throws_Exception() {
        throw new NullPointerException("test");
    }

    private Constructors_First_Throws_Exception(final Object o) { }
}