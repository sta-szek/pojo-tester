package pl.pojo.tester.api.assertion;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

import java.util.concurrent.TimeUnit;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

class WideClassPermutationTest {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void Should_Finish_A_Class_With_Many_Fields() {
        assertPojoMethodsFor(Wide.class).areWellImplemented();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void Should_Finish_When_A_Nested_Field_Has_Many_Fields() {
        assertPojoMethodsFor(new ClassAndFieldPredicatePair(Holder.class), new ClassAndFieldPredicatePair(Wide.class))
                .areWellImplemented();
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void Should_Finish_A_Graph_Of_Wide_Classes() {
        assertPojoMethodsForAll(Wide.class, OtherWide.class, Holder.class).areWellImplemented();
    }

    @Data
    @NoArgsConstructor
    static class Wide {
        private int f01;
        private int f02;
        private int f03;
        private int f04;
        private int f05;
        private int f06;
        private int f07;
        private int f08;
        private int f09;
        private int f10;
        private int f11;
        private int f12;
        private int f13;
        private int f14;
        private int f15;
        private int f16;
        private int f17;
        private int f18;
        private int f19;
        private int f20;
        private int f21;
        private int f22;
        private int f23;
        private int f24;
    }

    @Data
    @NoArgsConstructor
    static class OtherWide {
        private int g01;
        private int g02;
        private int g03;
        private int g04;
        private int g05;
        private int g06;
        private int g07;
        private int g08;
        private int g09;
        private int g10;
        private int g11;
        private int g12;
        private int g13;
        private int g14;
        private int g15;
        private int g16;
        private int g17;
        private int g18;
        private int g19;
        private int g20;
        private int g21;
        private int g22;
        private int g23;
        private int g24;
    }

    @Data
    @NoArgsConstructor
    static class Holder {
        private Wide wide;
    }
}
