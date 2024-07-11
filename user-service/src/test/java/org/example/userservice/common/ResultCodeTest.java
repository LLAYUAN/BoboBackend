package org.example.userservice.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultCodeTest {

    @Test
    void testGetCode() {
        assertThat(ResultCode.SUCCESS.getCode()).isEqualTo(0L);
        assertThat(ResultCode.FAILED.getCode()).isEqualTo(0L);
        assertThat(ResultCode.UNAUTHORIZED.getCode()).isEqualTo(0L);
        assertThat(ResultCode.FORBIDDEN.getCode()).isEqualTo(0L);
    }

    @Test
    void testGetMessage() {
        assertThat(ResultCode.SUCCESS.getMessage()).isEqualTo("message");
        assertThat(ResultCode.FAILED.getMessage()).isEqualTo("message");
        assertThat(ResultCode.UNAUTHORIZED.getMessage()).isEqualTo("message");
        assertThat(ResultCode.FORBIDDEN.getMessage()).isEqualTo("message");
    }
}
