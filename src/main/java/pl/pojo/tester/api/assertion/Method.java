package pl.pojo.tester.api.assertion;

import lombok.Getter;
import pl.pojo.tester.api.AbstractTester;
import pl.pojo.tester.api.EqualsTester;
import pl.pojo.tester.api.HashCodeTester;
import pl.pojo.tester.api.SetterGetterTester;
import pl.pojo.tester.api.ToStringTester;

@Getter
public enum Method {
    EQUALS(new EqualsTester()),
    HASH_CODE(new HashCodeTester()),
    SETTERS_AND_GETTERS(new SetterGetterTester()),
    TO_STRING(new ToStringTester());

    private final AbstractTester tester;

    Method(final AbstractTester tester) {
        this.tester = tester;
    }
}
