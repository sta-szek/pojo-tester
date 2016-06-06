package test.instantiator;


public class NoDefaultConstructor {

    private int a;
    private int b;
    private int c;
    private Object object;

    public NoDefaultConstructor(final int a) {
        this.a = a;
    }

    public NoDefaultConstructor(final Object object) {
        this.object = object;
    }
}
