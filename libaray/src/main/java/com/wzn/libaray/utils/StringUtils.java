package com.wzn.libaray.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luona on 2017/4/17.
 */

public class StringUtils {
    /**
     * check  phone
     *
     * @param str
     * @return
     */
    public static boolean matches(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
