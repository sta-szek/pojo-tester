package test.tostring;


import org.apache.commons.lang3.builder.ToStringBuilder;
import test.fields.TestEnum1;

public class ToStringWithoutField {

    private final int a = 1;
    private final float b = 1.43F;
    private final Object obj = null;
    private TestEnum1 testEnum;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("a", a)
                                        .append("b", b)
                                        .append("obj", obj)
                                        .toString();
    }


}
