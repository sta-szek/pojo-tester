package test.hashcode;


import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BadPojoHashCode {

    private int increment1;
    private int increment2;
    private int increment3;
    private int increment4;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(increment1)
                                    .append(increment2)
                                    .toHashCode();
    }
}
