package com.wzn.libaray.util

import com.wzn.libaray.utils.isContain
import com.wzn.libaray.utils.islegal
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.Assert.assertTrue

/**
 * Created by luona on 2018/1/16.
 */
class StringUtilsTest{
    @Test fun islegal_match_ReturnTrue(){
        assertTrue(islegal("\\w{3}","123"))
    }

    @Test fun islegal_match_ReturnFALSE(){
        assertFalse(islegal("\\w{8}","123"))
    }

    @Test fun isContain_match_ReturnTrue(){
        assertTrue(isContain("[a-zA-Z]","abcdefg"))
    }

    @Test fun isContain_match_ReturnFALSE(){
        assertFalse(isContain("[0-9]","abcdefg"))
    }
}
