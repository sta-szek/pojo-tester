package pl.pojo.tester.api;

/**
 * This is a base class for all instantiators.
 *
 * @author Piotr Joński
 * @since 0.8.0
 */
public abstract class AbstractObjectInstantiator {

    protected final Class<?> clazz;

    /**
     * Creates new instantiator for defined class.
     *
     * @param clazz class that will be instantiated
     */
    public AbstractObjectInstantiator(final Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Produces new instances of given class.
     *
     * @return new object that class is defined in constructor.
     */
    public abstract Object instantiate();

    /**
     * @return class defined in constructor.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "AbstractObjectInstantiator{clazz=" + clazz + '}';
    }
}
