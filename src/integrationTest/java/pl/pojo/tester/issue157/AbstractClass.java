package pl.pojo.tester.issue157;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
@Setter
public abstract class AbstractClass {

    private String a;
    private String b;
    private String c;
    private int d;

    public AbstractClass(final String a) {
        this.a = a;
    }

    public void setA(final String a) {
        this.a = a;
    }
}
