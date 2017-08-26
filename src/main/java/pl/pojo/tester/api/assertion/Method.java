package pl.pojo.tester.api.assertion;

import pl.pojo.tester.internal.tester.AbstractTester;
import pl.pojo.tester.internal.tester.ConstructorTester;
import pl.pojo.tester.internal.tester.EqualsFastTester;
import pl.pojo.tester.internal.tester.EqualsTester;
import pl.pojo.tester.internal.tester.GetterTester;
import pl.pojo.tester.internal.tester.HashCodeFastTester;
import pl.pojo.tester.internal.tester.HashCodeTester;
import pl.pojo.tester.internal.tester.SetterTester;
import pl.pojo.tester.internal.tester.ToStringTester;

/**
 * Declares methods that can be tested using POJO-TESTER.
 * <p>
 * For more documentation, please refer <a href="http://pojo.pl">POJO-TESTER User Guide documentation</a>
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public enum Method {
    EQUALS(new EqualsTester()),
    EQUALS_FAST(new EqualsFastTester()),
    HASH_CODE(new HashCodeTester()),
    HASH_CODE_FAST(new HashCodeFastTester()),
    SETTER(new SetterTester()),
    GETTER(new GetterTester()),
    TO_STRING(new ToStringTester()),
    CONSTRUCTOR(new ConstructorTester());

    private final AbstractTester tester;

    Method(final AbstractTester tester) {
        this.tester = tester;
    }

    public AbstractTester getTester() {
        return tester;
    }
}
