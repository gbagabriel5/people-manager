package com.gba.people.manager.util;

import java.util.regex.Pattern;

public class FormatPatternUtil {
    public static boolean isInvalidPattern(String string, String pattern) {
        return !Pattern.compile(pattern)
                .matcher(string)
                .matches();
    }
}
