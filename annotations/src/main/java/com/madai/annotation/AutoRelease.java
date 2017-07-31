package com.madai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Wind_Fantasy on 2017/7/24.
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.CLASS)
public @interface AutoRelease {
}
