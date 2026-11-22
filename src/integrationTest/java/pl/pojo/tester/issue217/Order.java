package pl.pojo.tester.issue217;

import java.util.Objects;

public class Order {

    private ValidatedCode code;

    public ValidatedCode getCode() {
        return code;
    }

    public void setCode(final ValidatedCode code) {
        this.code = code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return Objects.equals(code, ((Order) other).code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Order{code=" + code + '}';
    }
}
