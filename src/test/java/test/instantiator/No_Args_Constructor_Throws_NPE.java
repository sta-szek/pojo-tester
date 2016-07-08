package test.instantiator;

public class No_Args_Constructor_Throws_NPE {

    public No_Args_Constructor_Throws_NPE() {
        throw new NullPointerException("test");
    }
}
