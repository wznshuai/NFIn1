package com.wzn.libaray.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

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

    /**
     * 改变字体颜色
     *
     * @param str
     * @param color 原字体大小的倍数
     * @return
     */
    public static SpannableString strChangeColor(String str, int start, int end, int color) {
        SpannableString span = new SpannableString(str);
        span.setSpan(new ForegroundColorSpan(color), start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    /**
     * @param patternString 判断条件，为正则表达式
     * @param input         需要进行判断的字符串
     * @return true，符合给定条件；false，不符合给定条件
     */
    public static boolean islegal(String patternString, String input) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串是否包含匹配的字符
     *
     * @param input
     * @return
     */
    public static boolean isContain(String patternString, String input) {
        Pattern p = Pattern.compile(patternString);
        // 判断ascii码
        Matcher m = p.matcher(input);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
