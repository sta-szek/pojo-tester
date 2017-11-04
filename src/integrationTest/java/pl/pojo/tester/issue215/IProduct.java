package pl.pojo.tester.issue215;

/**
 * PatternBox: "IProduct" implementation.
 * <ul>
 * <li>defines the interface of objects the factory method creates.</li>
 * </ul>
 *
 * @author Dirk Ehms, <a href="http://www.patternbox.com">www.patternbox.com</a>
 * @author mnarouman
 */
public interface IProduct {
    String getName();

    double getPrixHT();

    double getPrixTTC();

    void setPrixTTC(double prixHT);

    boolean isExemptedTaxe();

    boolean isImported();

}