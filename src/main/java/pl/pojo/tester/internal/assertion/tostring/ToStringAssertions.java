package pl.pojo.tester.internal.assertion.tostring;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ToStringAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public ToStringAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }


    public void contains(final String fieldName, final Object value) {
        final String toString = objectUnderAssert.toString();
        final boolean result = containsAny(toString, fieldName, value);
        checkResult(result, new ContainsToStringAssertionError(classUnderTest, fieldName + "=" + getStringOf(value), toString));
    }

    public void doestNotContain(final String fieldName, final Object value) {
        final String toString = objectUnderAssert.toString();
        final boolean result = containsAny(toString, fieldName, value);
        checkResult(!result, new NotContainToStringAssertionError(classUnderTest, fieldName + "=" + getStringOf(value), toString));
    }

    private boolean containsAny(final String toString, final String fieldName, final Object value) {
        for (final String rendered : renderingsOf(value)) {
            if (toString.contains(fieldName + "=" + rendered)) {
                return true;
            }
        }
        return false;
    }

    private String getStringOf(final Object value) {
        return renderingsOf(value).get(0);
    }

    private List<String> renderingsOf(final Object value) {
        if (Objects.isNull(value)) {
            return Collections.singletonList("");
        }
        if (!value.getClass().isArray()) {
            if (value instanceof CharSequence || value instanceof Character) {
                final List<String> renderings = new ArrayList<>(2);
                renderings.add(value.toString());
                renderings.add("'" + value + "'");
                return renderings;
            }
            return Collections.singletonList(value.toString());
        }
        final List<String> renderings = new ArrayList<>(2);
        renderings.add(bracketArray(value));
        renderings.add(braceArray(value));
        return renderings;
    }

    private String bracketArray(final Object value) {
        if (value instanceof Object[]) {
            return Arrays.toString((Object[]) value);
        }
        final int length = Array.getLength(value);
        final Object[] boxed = new Object[length];
        for (int i = 0; i < length; i++) {
            boxed[i] = Array.get(value, i);
        }
        return Arrays.toString(boxed);
    }

    private String braceArray(final Object value) {
        final int length = Array.getLength(value);
        final StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            final Object element = Array.get(value, i);
            builder.append(element == null ? "<null>" : String.valueOf(element));
        }
        return builder.append('}').toString();
    }


    private void checkResult(final boolean pass, final AbstractToStringAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
