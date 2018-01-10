package com.wzn.libaray.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.TextView

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by luona on 2017/4/17.
 */

/**
 * check  phone
 *
 * @param str
 * @return
 */
fun matches(str: String, pattern: String): Boolean {
    val p = Pattern.compile(pattern)
    val m = p.matcher(str)
    return m.matches()
}

/**
 * @param patternString 判断条件，为正则表达式
 * @param input         需要进行判断的字符串
 * @return true，符合给定条件；false，不符合给定条件
 */
fun islegal(patternString: String, input: String): Boolean {
    val pattern = Pattern.compile(patternString)
    val matcher = pattern.matcher(input)
    return if (matcher.matches()) {
        true
    } else {
        false
    }
}

/**
 * 字符串是否包含匹配的字符
 *
 * @param input
 * @return
 */
fun isContain(patternString: String, input: String): Boolean {
    val p = Pattern.compile(patternString)
    // 判断ascii码
    val m = p.matcher(input)
    return if (m.find()) {
        true
    } else false
}

/**
 * 改变字体颜色
 *
 * @param str
 * @param color 原字体大小的倍数
 * @return
 */
fun strChangeColor(str: CharSequence, start: Int, end: Int, color: Int): SpannableString {
    val span = SpannableString(str)
    span.setSpan(ForegroundColorSpan(color), start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return span
}

/**
 * 改变字体颜色
 *
 * @param str
 * @param relativeSize 原字体大小的倍数
 * @return
 */
fun strChangeSize(str: CharSequence, start: Int, end: Int, relativeSize: Float): SpannableString {
    val span = SpannableString(str)
    span.setSpan(RelativeSizeSpan(relativeSize), start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return span
}

fun strClickable(textView: TextView,
                 str: CharSequence, start: Int, end: Int,
                 actionOnClick: (() -> Unit)?,
                 actionStyleChange: ((ds: TextPaint?) -> Unit)?)
        : SpannableString {
    val span = SpannableString(str)
    span
            .setSpan(object : ClickableSpan() {
                override fun onClick(widget: View?) {
                    actionOnClick?.invoke()
                }

                override fun updateDrawState(ds: TextPaint?) {
                    actionStyleChange?.invoke(ds)
                }
            },
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.movementMethod = LinkMovementMethod.getInstance()//不可省略，否则不相应点击事件
    return span
}
