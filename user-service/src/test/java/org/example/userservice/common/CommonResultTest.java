//package org.example.userservice.common;
//
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class CommonResultTest {
//
//    @Test
//    void testSuccess() {
//        String data = "Test Data";
//        CommonResult<String> result = CommonResult.success(data);
//
//        assertThat(result.getCode()).isEqualTo(ResultCode.SUCCESS.getCode());
//        assertThat(result.getMessage()).isEqualTo(ResultCode.SUCCESS.getMessage());
//        assertThat(result.getData()).isEqualTo(data);
//    }
//
//    @Test
//    void testFailed() {
//        String errorMessage = "Operation failed";
//        CommonResult<Object> result = CommonResult.failed(errorMessage);
//
//        assertThat(result.getCode()).isEqualTo(ResultCode.FAILED.getCode());
//        assertThat(result.getMessage()).isEqualTo(errorMessage);
//        assertThat(result.getData()).isNull();
//    }
//
//    @Test
//    void testUnauthorized() {
//        String errorMessage = "Unauthorized access";
//        CommonResult<Object> result = CommonResult.unauthorized(errorMessage);
//
//        assertThat(result.getCode()).isEqualTo(ResultCode.UNAUTHORIZED.getCode());
//        assertThat(result.getMessage()).isEqualTo(errorMessage);
//        assertThat(result.getData()).isNull();
//    }
//
//    @Test
//    void testForbidden() {
//        String errorMessage = "Forbidden access";
//        CommonResult<Object> result = CommonResult.forbidden(errorMessage);
//
//        assertThat(result.getCode()).isEqualTo(ResultCode.FORBIDDEN.getCode());
//        assertThat(result.getMessage()).isEqualTo(errorMessage);
//        assertThat(result.getData()).isNull();
//    }
//
//    @Test
//    void testGettersAndSetters() {
//        CommonResult<String> result = new CommonResult<>(200, "Success", "Data");
//
//        result.setCode(404);
//        result.setMessage("Not Found");
//        result.setData("New Data");
//
//        assertThat(result.getCode()).isEqualTo(404);
//        assertThat(result.getMessage()).isEqualTo("Not Found");
//        assertThat(result.getData()).isEqualTo("New Data");
//    }
//
//    @Test
//    void testToString() {
//        CommonResult<String> result = new CommonResult<>(200, "Success", "Data");
//
//        String expectedToString = "CommonResult(code=200, message=Success, data=Data)";
//        assertThat(result.toString()).isEqualTo(expectedToString);
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        CommonResult<String> result1 = new CommonResult<>(200, "Success", "Data");
//        CommonResult<String> result2 = new CommonResult<>(200, "Success", "Data");
//
//        assertThat(result1).isEqualTo(result2);
//        assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
//
//        result2.setData("New Data");
//
//        assertThat(result1).isNotEqualTo(result2);
//        assertThat(result1.hashCode()).isNotEqualTo(result2.hashCode());
//    }
//
//    @Test
//    void testBuilderPattern() {
//        CommonResult<String> result1 = new CommonResult<>(200, "操作成功", "Data");
//        CommonResult<String> result2 = CommonResult.success("Data");
//
//        assertThat(result1).isEqualTo(result2);
//        assertThat(result1.hashCode()).isEqualTo(result2.hashCode());
//    }
//
//    @Test
//    void testNotEqualsDifferentCode() {
//        CommonResult<String> result1 = new CommonResult<>(200, "Success", "Data");
//        CommonResult<String> result2 = new CommonResult<>(404, "Success", "Data");
//
//        assertThat(result1).isNotEqualTo(result2);
//        assertThat(result1.hashCode()).isNotEqualTo(result2.hashCode());
//    }
//
//    @Test
//    void testNotEqualsDifferentMessage() {
//        CommonResult<String> result1 = new CommonResult<>(200, "Success", "Data");
//        CommonResult<String> result2 = new CommonResult<>(200, "Failure", "Data");
//
//        assertThat(result1).isNotEqualTo(result2);
//        assertThat(result1.hashCode()).isNotEqualTo(result2.hashCode());
//    }
//
//    @Test
//    void testNotEqualsDifferentData() {
//        CommonResult<String> result1 = new CommonResult<>(200, "Success", "Data");
//        CommonResult<String> result2 = new CommonResult<>(200, "Success", "Different Data");
//
//        assertThat(result1).isNotEqualTo(result2);
//        assertThat(result1.hashCode()).isNotEqualTo(result2.hashCode());
//    }
//}
