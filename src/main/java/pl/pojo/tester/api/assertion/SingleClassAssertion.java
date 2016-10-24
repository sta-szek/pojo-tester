package pl.pojo.tester.api.assertion;

import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import java.util.Arrays;

class SingleClassAssertion extends AbstractAssertion {

    private final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair;
    private final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs;

    SingleClassAssertion(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        super();
        this.baseClassAndFieldPredicatePair = baseClassAndFieldPredicatePair;
        this.classAndFieldPredicatePairs = Arrays.copyOf(classAndFieldPredicatePairs, classAndFieldPredicatePairs.length);
    }

    @Override
    protected void testImplementation() {
        testers.forEach(tester -> tester.test(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs));
    }
}
