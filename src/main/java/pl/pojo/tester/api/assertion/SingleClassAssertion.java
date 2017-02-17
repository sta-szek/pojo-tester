package pl.pojo.tester.api.assertion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import java.util.Arrays;


class SingleClassAssertion extends AbstractAssertion {

    private static final Logger LOGGER = LoggerFactory.getLogger(SingleClassAssertion.class);

    private final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair;
    private final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs;

    SingleClassAssertion(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        super();
        this.baseClassAndFieldPredicatePair = baseClassAndFieldPredicatePair;
        this.classAndFieldPredicatePairs = Arrays.copyOf(classAndFieldPredicatePairs,
                                                         classAndFieldPredicatePairs.length);
    }

    @Override
    protected void runAssertions() {
        logTestersAndClasses(LOGGER);

        testers.forEach(tester -> tester.test(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs));
    }

    @Override
    protected void logTestersAndClasses(final Logger logger, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        if (LOGGER.isDebugEnabled()) {
            final ClassAndFieldPredicatePair[] classes = new ClassAndFieldPredicatePair[this.classAndFieldPredicatePairs.length +
                                                                                        1];
            classes[0] = baseClassAndFieldPredicatePair;
            System.arraycopy(this.classAndFieldPredicatePairs, 0, classes, 1, classes.length - 1);
            super.logTestersAndClasses(LOGGER, classes);
        }
    }
}
