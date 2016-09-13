package pl.pojo.tester.api.assertion;

public class GoodPojo {

    private boolean booleanType;

    @Override
    public String
    toString() {
        return "GoodPojo{" +
               "booleanType=" + booleanType +
               '}';
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GoodPojo goodPojo = (GoodPojo) o;

        return booleanType == goodPojo.booleanType;

    }

    @Override
    public int hashCode() {
        return (booleanType
                ? 1
                : 0);
    }
}
