package classesForTest.instantiator;

public class One_Arg_Constructor_Throws_NPE {

    public One_Arg_Constructor_Throws_NPE(final Object o) {
        throw new NullPointerException("test");
    }
}
