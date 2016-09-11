package pl.pojo.tester;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pojo.tester.field.AbstractFieldValueChanger;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractAssetion {

    private static final Set<AbstractTester> DEFAULT_TESTERS;

    static {
        DEFAULT_TESTERS = new HashSet<>();
        Arrays.stream(Method.values())
              .map(Method::getTester)
              .forEach(DEFAULT_TESTERS::add);
    }

    Set<AbstractTester> testers = new HashSet<>();
    private AbstractFieldValueChanger abstractFieldValueChanger;

    public AbstractAssetion using(final AbstractFieldValueChanger abstractFieldValueChanger) {
        this.abstractFieldValueChanger = abstractFieldValueChanger;
        return this;
    }

    public AbstractAssetion testing(final Method... methods) {
        Arrays.asList(methods)
              .forEach(this::testing);
        return this;
    }

    public AbstractAssetion testing(final Method method) {
        final AbstractTester tester = method.getTester();
        this.testers.add(tester);
        return this;
    }

    public void areWellImplemented() {
        if (testers.isEmpty()) {
            testers = DEFAULT_TESTERS;
        }
        if (abstractFieldValueChanger != null) {
            testers.forEach(tester -> tester.setFieldValuesChanger(abstractFieldValueChanger));
        }

        testImplementation();
    }

    protected abstract void testImplementation();

}
