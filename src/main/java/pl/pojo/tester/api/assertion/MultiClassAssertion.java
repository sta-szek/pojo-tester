package pl.pojo.tester.api.assertion;

import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import java.util.List;

class MultiClassAssertion extends AbstractAssertion {

    private final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs;

    MultiClassAssertion(final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs) {
        super();
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    protected void testImplementation() {
        final ClassAndFieldPredicatePair[] classes = classAndFieldPredicatePairs.toArray(new ClassAndFieldPredicatePair[classAndFieldPredicatePairs.size()]);
        testers.forEach(tester -> tester.testAll(classes));
    }
}
