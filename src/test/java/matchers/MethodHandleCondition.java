package matchers;

import java.lang.invoke.MethodHandle;
import org.assertj.core.api.Condition;

public class MethodHandleCondition extends Condition<MethodHandle> {

    private final MethodHandle expectedMethodHandle;

    public MethodHandleCondition(final MethodHandle expectedMethodHandle) {
        this.expectedMethodHandle = expectedMethodHandle;
    }

    @Override
    public boolean matches(final MethodHandle actualMethodHandle) {
        return expectedMethodHandle.type()
                                   .equals(actualMethodHandle.type());
    }
}
