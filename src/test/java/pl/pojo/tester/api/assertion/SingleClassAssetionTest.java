package pl.pojo.tester.api.assertion;

import com.google.common.collect.Sets;
import lombok.Data;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.tester.EqualsTester;

import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;


public class SingleClassAssetionTest {

    @Test
    public void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(A.class);
        final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs = {classAndFieldPredicatePair};
        final SingleClassAssertion singleClassAssetion = new SingleClassAssertion(classAndFieldPredicatePair,
                                                                                  classAndFieldPredicatePairs);
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        setInternalState(singleClassAssetion, "testers", Sets.newHashSet(equalsTester1, equalsTester2));

        // when
        singleClassAssetion.runAssertions();

        // then
        verify(equalsTester1, only()).test(classAndFieldPredicatePair, classAndFieldPredicatePairs);
        verify(equalsTester2, only()).test(classAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    @Data
    private class A {
        private int a;
    }

}
