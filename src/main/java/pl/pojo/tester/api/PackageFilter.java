package pl.pojo.tester.api;

/**
 * Interface for package filtering.
 *
 * @author Piotr Joński
 * @since 0.4.0
 */
@FunctionalInterface
public interface PackageFilter {

    /**
     * Returns classes filtered by filter.
     *
     * @return classes
     */
    Class<?>[] getClasses();
}
