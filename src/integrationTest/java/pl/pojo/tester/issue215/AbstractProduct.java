package pl.pojo.tester.issue215;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public abstract class AbstractProduct implements Taxable {
    @NonNull
    private String name;

    @NonNull
    private double prixHT;

    @NonNull
    private boolean imported;

    @Setter
    private double prixTTC;

    @Setter
    private double taxe;

    @Override
    public boolean isExemptedTaxe() {
        return false;
    }
}