package com.ll.exam.jwt_login;

import com.ll.exam.jwt_login.app.util.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTests {
    @Test
    @DisplayName("Util.mapOf")
    void t1() {
        Map<String, Integer> ages = Util.mapOf("영수", 22, "철수", 21);

        assertThat(ages.get("영수")).isEqualTo(22);
        assertThat(ages.get("철수")).isEqualTo(21);
    }
}
