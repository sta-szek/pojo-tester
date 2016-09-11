package pl.pojo.tester;

import lombok.Getter;

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
