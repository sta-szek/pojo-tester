package pl.pojo.tester;

class SingleClassAssetion extends AbstractAssetion {

    private final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair;
    private final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs;

    SingleClassAssetion(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        this.baseClassAndFieldPredicatePair = baseClassAndFieldPredicatePair;
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    public void testImplementation() {
        testers.forEach(tester -> tester.test(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs));
    }
}
