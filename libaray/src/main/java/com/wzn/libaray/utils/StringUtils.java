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
}
