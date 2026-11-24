package pl.pojo.tester.issue248;

class BrokenEquals {

    private Double testField;

    BrokenEquals(final Double testField) {
        this.testField = testField;
    }

    public Double getTestField() {
        return testField;
    }

    public void setTestField(final Double testField) {
        this.testField = testField;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final BrokenEquals that = (BrokenEquals) other;
        if (!(testField == null && that.testField == null)
                || (testField != null && that.testField != null && Double.compare(testField, that.testField) != 0)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return testField == null ? 0 : testField.hashCode();
    }
}
