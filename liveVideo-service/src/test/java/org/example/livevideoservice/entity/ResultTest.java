package org.example.livevideoservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    private Result resultUnderTest;

    @BeforeEach
    void setUp() {
        resultUnderTest = new Result(0, "message", "data");
    }

    @Test
    void testStatusGetterAndSetter() {
        final int status = 0;
        resultUnderTest.setStatus(status);
        assertThat(resultUnderTest.getStatus()).isEqualTo(status);
    }

    @Test
    void testMessageGetterAndSetter() {
        final String message = "message";
        resultUnderTest.setMessage(message);
        assertThat(resultUnderTest.getMessage()).isEqualTo(message);
    }

    @Test
    void testDataGetterAndSetter() {
        final Object data = "data";
        resultUnderTest.setData(data);
        assertThat(resultUnderTest.getData()).isEqualTo(data);
    }

    @Test
    void testSuccess1() {
        // Run the test
        final Result result = Result.success("data");
        assertThat(result.getStatus()).isEqualTo(0);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("data");
    }

    @Test
    void testSuccess2() {
        // Run the test
        final Result result = Result.success();
        assertThat(result.getStatus()).isEqualTo(0);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("data");
    }

    @Test
    void testError() {
        // Run the test
        final Result result = Result.error("message");
        assertThat(result.getStatus()).isEqualTo(0);
        assertThat(result.getMessage()).isEqualTo("message");
        assertThat(result.getData()).isEqualTo("data");
    }
}
