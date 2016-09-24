package pl.pojo.tester.api.assertion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import matchers.ClassAndFieldPredicatePairArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.EqualsTester;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(JUnitPlatform.class)
public class MultiClassAssetionTest {

    @Test
    public void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(A.class);
        final MultiClassAssetion multiClassAssetion = new MultiClassAssetion(Lists.newArrayList(classAndFieldPredicatePair));
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        setInternalState(multiClassAssetion, "testers", Sets.newHashSet(equalsTester1, equalsTester2));

        // when
        multiClassAssetion.testImplementation();

        // then
        verify(equalsTester1, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
        verify(equalsTester2, only()).testAll(argThat(new ClassAndFieldPredicatePairArgumentMatcher(A.class, "a")));
    }

    @Data
    private class A {
        private int a;
    }
}
