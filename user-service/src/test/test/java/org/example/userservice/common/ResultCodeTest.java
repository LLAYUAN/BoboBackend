package org.example.userservice.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultCodeTest {

    @Test
    void testGetCode() {
        assertThat(ResultCode.SUCCESS.getCode()).isEqualTo(200L);
        assertThat(ResultCode.FAILED.getCode()).isEqualTo(500L);
        assertThat(ResultCode.UNAUTHORIZED.getCode()).isEqualTo(401L);
        assertThat(ResultCode.FORBIDDEN.getCode()).isEqualTo(403L);
    }

    @Test
    void testGetMessage() {
        assertThat(ResultCode.SUCCESS.getMessage()).isEqualTo("操作成功");
        assertThat(ResultCode.FAILED.getMessage()).isEqualTo("操作失败");
        assertThat(ResultCode.UNAUTHORIZED.getMessage()).isEqualTo("未认证或Token已过期");
        assertThat(ResultCode.FORBIDDEN.getMessage()).isEqualTo("没有足够权限");
    }
}
