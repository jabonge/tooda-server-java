package com.ddd.tooda.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;


class HashtagUtilTest {

    @Test
    void findHashtags() {
        String content = "안녕하세요 #해쉬태그를 잘 검출 할까요?\n #일상, #코딩";
        List<String> hashtags = HashtagUtil.findHashtags(content);

        System.out.println(hashtags);
        Assertions.assertEquals(hashtags.size(),3);
    }
}