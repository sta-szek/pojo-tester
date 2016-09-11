package org.pojo.tester;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import matchers.ClassAndFieldPredicatePairArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(JUnitPlatform.class)
public class MultiClassAssetionTest {

    @Test
    public void Should_Test_Against_Each_Tester() {
        // given
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(A.class);
        final MultiClassAssetion multiClassAssetion = new MultiClassAssetion(Lists.newArrayList(classAndFieldPredicatePair));
        final EqualsTester equalsTester1 = mock(EqualsTester.class);
        final EqualsTester equalsTester2 = mock(EqualsTester.class);
        Whitebox.setInternalState(multiClassAssetion, "testers", Sets.newHashSet(equalsTester1, equalsTester2));

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
