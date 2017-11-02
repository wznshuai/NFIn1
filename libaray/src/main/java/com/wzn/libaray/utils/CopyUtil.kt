package com.wzn.libaray.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Created by luona on 2017/11/2.
 */
fun copyToClipBoard(context: Context, text: String, label: String? = null) {
    val clipBoard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(label, text)
    clipBoard.primaryClip = clipData
}

fun getDataFromClipBoard(context: Context): String {
    val clipBoard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    var resultString = ""
    if (!clipBoard.hasPrimaryClip())
        Logger.d(context.javaClass.simpleName, "clipboard is empty")
    else {
        val clipData = clipBoard.primaryClip
        val count = clipData.itemCount

        for (i in 0 until count) {

            val item = clipData.getItemAt(i)
            val str = item
                    .coerceToText(context)

            resultString += str
        }
    }
    return resultString
}