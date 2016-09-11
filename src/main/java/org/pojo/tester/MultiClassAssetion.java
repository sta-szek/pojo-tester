package org.pojo.tester;

import java.util.List;

class MultiClassAssetion extends AbstractAssetion {

    private final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs;

    MultiClassAssetion(final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs) {
        this.classAndFieldPredicatePairs = classAndFieldPredicatePairs;
    }

    @Override
    public void testImplementation() {
        final ClassAndFieldPredicatePair[] classes = classAndFieldPredicatePairs.toArray(new ClassAndFieldPredicatePair[classAndFieldPredicatePairs.size()]);
        testers.forEach(tester -> tester.testAll(classes));
    }
}
