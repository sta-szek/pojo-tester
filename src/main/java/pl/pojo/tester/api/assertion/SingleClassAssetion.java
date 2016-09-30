package pl.pojo.tester.api.assertion;

import pl.pojo.tester.api.ClassAndFieldPredicatePair;

class SingleClassAssetion extends AbstractAssetion {

    private final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair;
    private final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs;

    SingleClassAssetion(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        this.baseClassAndFieldPredicatePair = baseClassAndFieldPredicatePair;
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    protected void testImplementation() {
        testers.forEach(tester -> tester.test(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs));
    }
}
