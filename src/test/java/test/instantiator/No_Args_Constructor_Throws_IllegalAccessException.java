package test.instantiator;

public class No_Args_Constructor_Throws_IllegalAccessException {

    public No_Args_Constructor_Throws_IllegalAccessException() throws IllegalAccessException {
        throw new IllegalAccessException("test");
    }
}
