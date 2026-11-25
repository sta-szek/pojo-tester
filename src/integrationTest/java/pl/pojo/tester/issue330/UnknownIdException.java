package pl.pojo.tester.issue330;

import java.util.Objects;

public class UnknownIdException extends RuntimeException {

    private String code;

    public UnknownIdException() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
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
        return Objects.equals(code, ((UnknownIdException) other).code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "UnknownIdException{code=" + code + '}';
    }
}
