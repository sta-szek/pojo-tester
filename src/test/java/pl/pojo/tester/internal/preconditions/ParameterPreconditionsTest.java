package pl.pojo.tester.internal.preconditions;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotNull;

@RunWith(JUnitPlatform.class)
public class ParameterPreconditionsTest {

    @Test
    public void Should_Throw_Exception_When_String_Parameter_Is_Empty_String() {
        // given

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", ""));

        // then
        assertThat(result).isInstanceOf(BlankParameterException.class);
    }

    @Test
    public void Should_Throw_Exception_When_String_Parameter_Is_White_Char_String() {
        // given

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", " "));

        // then
        assertThat(result).isInstanceOf(BlankParameterException.class);
    }

    @Test
    public void Should_Throw_Exception_When_String_Parameter_Is_Null() {
        // given
        final String parameterValue = null;

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(NullParameterException.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_String_Parameter_Is_Not_Blank() {
        // given

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", "parameterValue"));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_At_Least_One_Parameter_Is_Empty_String() {
        // given
        final String[] parameterValue = new String[]{"valid", ""};


        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(BlankParameterException.class);
    }

    @Test
    public void Should_Throw_Exception_When_At_Least_One_Parameter_Is_White_Char_String() {
        // given
        final String[] parameterValue = new String[]{"valid", " "};

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(BlankParameterException.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_All_Parameters_Are_Not_Blank() {
        // given
        final String[] parameterValue = new String[]{"valid", "valid"};

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", parameterValue));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_At_Least_One_Parameter_Is_Null() {
        // given
        final String[] parameterValue = new String[]{"valid", null};

        // when
        final Throwable result = catchThrowable(() -> checkNotBlank("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(NullParameterException.class);
    }

    @Test
    public void Should_Throw_Exception_When_Object_Parameter_Is_Null() {
        // given
        final Object parameterValue = null;

        // when
        final Throwable result = catchThrowable(() -> checkNotNull("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(NullParameterException.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_Object_Parameter_Is_Not_Null() {
        // given
        final Object parameterValue = new Object();

        // when
        final Throwable result = catchThrowable(() -> checkNotNull("parameterName", parameterValue));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_At_Least_One_Object_Parameter_Is_Null() {
        // given
        final Object[] parameterValue = {new Object(), null};

        // when
        final Throwable result = catchThrowable(() -> checkNotNull("parameterName", parameterValue));

        // then
        assertThat(result).isInstanceOf(NullParameterException.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_All_Parameters_Are_Not_Null() {
        // given
        final Object[] parameterValue = {new Object(), new Object()};

        // when
        final Throwable result = catchThrowable(() -> checkNotNull("parameterName", parameterValue));

        // then
        assertThat(result).isNull();
    }


}
