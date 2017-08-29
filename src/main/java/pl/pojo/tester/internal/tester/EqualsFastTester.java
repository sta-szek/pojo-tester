package pl.pojo.tester.internal.tester;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public class EqualsFastTester extends EqualsTester {

    public EqualsFastTester() {
        super();
        setThoroughTesting(false);
    }

    public EqualsFastTester(AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
        setThoroughTesting(false);
    }
}
