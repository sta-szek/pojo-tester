package pl.pojo.tester.issue328;

import java.util.Objects;

public class SheetRow {

    private Integer tRow;

    public Integer getTRow() {
        return tRow;
    }

    public void setTRow(final Integer tRow) {
        this.tRow = tRow;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(tRow, ((SheetRow) other).tRow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tRow);
    }

    @Override
    public String toString() {
        return "SheetRow{tRow=" + tRow + '}';
    }
}
