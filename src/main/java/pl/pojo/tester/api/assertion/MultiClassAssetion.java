package pl.pojo.tester.api.assertion;

import java.util.List;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

class MultiClassAssetion extends AbstractAssetion {

    private final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs;

    MultiClassAssetion(final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs) {
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    protected void testImplementation() {
        final ClassAndFieldPredicatePair[] classes = classAndFieldPredicatePairs.toArray(new ClassAndFieldPredicatePair[classAndFieldPredicatePairs.size()]);
        testers.forEach(tester -> tester.testAll(classes));
    }
}
