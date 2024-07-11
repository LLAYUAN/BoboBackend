package org.example.userservice.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonResultTest {

    private CommonResult<String> commonResultUnderTest;

    @BeforeEach
    void setUp() {
        commonResultUnderTest = new CommonResult<>(0L, "message", "data");
    }

    @Test
    void testSuccess() {
        // Run the test
        final CommonResult<String> result = CommonResult.success("data");
        assertThat(result.getCode()).isEqualTo(0L);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("result");
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isEqualTo(0);
        assertThat(result.toString()).isEqualTo("result");
    }

    @Test
    void testFailed() {
        // Run the test
        final CommonResult<String> result = CommonResult.failed("message");
        assertThat(result.getCode()).isEqualTo(0L);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("result");
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isEqualTo(0);
        assertThat(result.toString()).isEqualTo("result");
    }

    @Test
    void testUnauthorized() {
        // Run the test
        final CommonResult<String> result = CommonResult.unauthorized("message");
        assertThat(result.getCode()).isEqualTo(0L);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("result");
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isEqualTo(0);
        assertThat(result.toString()).isEqualTo("result");
    }

    @Test
    void testForbidden() {
        // Run the test
        final CommonResult<String> result = CommonResult.forbidden("message");
        assertThat(result.getCode()).isEqualTo(0L);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("result");
        assertThat(result.equals("o")).isFalse();
        assertThat(result.hashCode()).isEqualTo(0);
        assertThat(result.toString()).isEqualTo("result");
    }

    @Test
    void testCodeGetterAndSetter() {
        final long code = 0L;
        commonResultUnderTest.setCode(code);
        assertThat(commonResultUnderTest.getCode()).isEqualTo(code);
    }

    @Test
    void testMessageGetterAndSetter() {
        final String message = "message";
        commonResultUnderTest.setMessage(message);
        assertThat(commonResultUnderTest.getMessage()).isEqualTo(message);
    }

    @Test
    void testDataGetterAndSetter() {
        final String data = "data";
        commonResultUnderTest.setData(data);
        assertThat(commonResultUnderTest.getData()).isEqualTo(data);
    }

    @Test
    void testEquals() {
        assertThat(commonResultUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(commonResultUnderTest.canEqual("other")).isFalse();
    }

    @Test
    void testHashCode() {
        assertThat(commonResultUnderTest.hashCode()).isEqualTo(0);
    }

    @Test
    void testToString() {
        assertThat(commonResultUnderTest.toString()).isEqualTo("result");
    }
}
