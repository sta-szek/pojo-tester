package pl.pojo.tester.api;


import java.io.IOException;

/**
 * Exception is thrown when package or converted to filename package does not exist in file system.
 *
 * @author Piotr Joński
 * @since 0.5.0
 */
public class PacakgeFilterException extends RuntimeException {

    /**
     * Instantiates exception.
     *
     * @param packageName package name or file of package
     * @param cause       cause, which raised this exception
     */
    public PacakgeFilterException(final String packageName, final IOException cause) {
        super(createMessage(packageName), cause);
    }

    private static String createMessage(final String packageName) {
        return String.format("Package '%s' does not exist.", packageName);
    }
}
