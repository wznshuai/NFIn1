package com.wzn.libaray.sample

import com.madai.annotation.AutoRelease


/**
 * Created by Wind_Fantasy on 2017/7/24.
 */
class TestAnnotations {
    companion object {
        @AutoRelease
        fun testAutoRelease() {
            println("释放来自于TestAnnotations")
        }
    }
}