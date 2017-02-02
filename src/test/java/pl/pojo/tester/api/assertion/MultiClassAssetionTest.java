package pl.pojo.tester.api.assertion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import helpers.ClassAndFieldPredicatePairArgumentMatcher;
import lombok.Data;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.tester.EqualsTester;

import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;


public class MultiClassAssetionTest {

    @Test
    public void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(A.class);
        final MultiClassAssertion multiClassAssetion = new MultiClassAssertion(Lists.newArrayList(
                classAndFieldPredicatePair));
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        setInternalState(multiClassAssetion, "testers", Sets.newHashSet(equalsTester1, equalsTester2));

        // when
        multiClassAssetion.runAssertions();

        // then
        verify(equalsTester1, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
        verify(equalsTester2, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
    }

    @Data
    private class A {
        private int a;
    }
}
