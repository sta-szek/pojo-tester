package issues.issue157;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public abstract class AbstractClass {

    private String a;

    public AbstractClass(final String a) {
        this.a = a;
    }

    public void setA(final String a) {
        this.a = a;
    }
}
