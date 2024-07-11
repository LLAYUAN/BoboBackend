package org.example.userservice.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonResultTest {

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
}
