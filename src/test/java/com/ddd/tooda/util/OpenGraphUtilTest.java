package com.ddd.tooda.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OpenGraphUtilTest {

    @DisplayName("OG 응답이 있을때")
    @Test
    void test1() {
        String targetUrl = "https://naver.com";
        OpenGraphUtil.OGResponse ogResponse = OpenGraphUtil.getOgInfo(targetUrl);

        Assertions.assertNotNull(ogResponse.getOgUrl());
    }

    @DisplayName("OG 응답이 없거나 잘못된 Url")
    @Test
    void test2() {
        String targetUrl = "https://abc.abc";
        OpenGraphUtil.OGResponse ogResponse = OpenGraphUtil.getOgInfo(targetUrl);

        Assertions.assertNull(ogResponse);
    }

}