package org.example.userservice.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommonUtilTest {

    @Test
    void testStringToDate() {
        assertThat(CommonUtil.stringToDate("2020-01-01")).isEqualTo(Date.valueOf(LocalDate.of(2020, 1, 1)));
    }

    @Test
    void testStringToDate_NullInput() {
        // Invoke the method with null input
        Date result = CommonUtil.stringToDate(null);

        // Assert that the result is null
        assertNull(result, "Expected null when input is null");
    }

    @Test
    void testStringToDate_EmptyTrimmedInput() {
        // Invoke the method with empty trimmed input
        Date result = CommonUtil.stringToDate("   ");

        // Assert that the result is null
        assertNull(result, "Expected null when input is empty after trimming");
    }
}
