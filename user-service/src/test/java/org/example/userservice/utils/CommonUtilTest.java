package org.example.userservice.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CommonUtilTest {

    @Test
    void testStringToDate() {
        assertThat(CommonUtil.stringToDate("date")).isEqualTo(Date.valueOf(LocalDate.of(2020, 1, 1)));
    }
}
