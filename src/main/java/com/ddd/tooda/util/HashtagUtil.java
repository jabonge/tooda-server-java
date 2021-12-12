package com.ddd.tooda.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashtagUtil {

   public static List<String> findHashtags(String content) {
        String regex = "\\#([0-9a-zA-Z가-힣]{2,})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);

        Set<String> hashtags = new HashSet<>();

        while (m.find()) {
            hashtags.add(m.group().substring(1));
        }

        return new ArrayList(hashtags);
    }
}
