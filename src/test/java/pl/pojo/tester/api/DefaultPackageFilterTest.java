package pl.pojo.tester.api;

import classesForTest.packageFilter.A;
import classesForTest.packageFilter.B;
import classesForTest.packageFilter.C;
import classesForTest.packageFilter.next.D;
import classesForTest.packageFilter.next.E;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitPlatform.class)
public class DefaultPackageFilterTest {

    @Test
    public void Should_Return_Classes_From_Given_Package() {
        // given
        final Class<?>[] expectedClasses = new Class[]{A.class, B.class, C.class, D.class, E.class};

        // when
        final Class<?>[] result = DefaultPackageFilter.forClass(A.class)
                                                      .getClasses();

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }

    @Test
    public void Should_Return_Classes_From_Given_Package_Name() {
        // given
        final Class<?>[] expectedClasses = new Class[]{D.class, E.class};

        // when
        final Class<?>[] result = DefaultPackageFilter.forPackage("classesForTest.packageFilter.next")
                                                      .getClasses();

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }
}