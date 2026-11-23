package pl.pojo.tester.internal.instantiator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;

class RecursiveTypeInstantiatorTest {

    @Test
    void Should_Instantiate_A_Class_Whose_Constructor_Takes_Itself() {
        final Throwable result = catchThrowable(() -> assertPojoMethodsFor(Node.class).testing(GETTER)
                                                                                     .areWellImplemented());

        assertThat(result).isNull();
    }

    @Test
    void Should_Instantiate_Two_Classes_That_Take_Each_Other() {
        final Throwable result = catchThrowable(() -> assertPojoMethodsFor(Left.class).testing(GETTER)
                                                                                     .areWellImplemented());

        assertThat(result).isNull();
    }

    private static final class Node {
        private final Node next;
        private final int value;

        private Node(final Node next, final int value) {
            this.next = next;
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public int getValue() {
            return value;
        }
    }

    private static final class Left {
        private final Right right;

        private Left(final Right right) {
            this.right = right;
        }

        public Right getRight() {
            return right;
        }
    }

    private static final class Right {
        private final Left left;

        private Right(final Left left) {
            this.left = left;
        }

        public Left getLeft() {
            return left;
        }
    }
}
