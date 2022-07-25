package pl.pojo.tester.issue215;

/**
 * A taxe decorator interface
 *
 * @author michel
 */
public interface Taxable extends IProduct {
    double getTaxe();

    void setTaxe(double taxe);

    /**
     * Compute prixTTC and the product taxe
     */
    void applyTaxe();
}
