package com.wzn.libaray.event;

/**
 * Created by Wind_Fantasy on 15/7/9.
 */
public class JSEvent {
    public String functionName;
    public String functionParams;

    public JSEvent(String functionName, String functionParams) {
        this.functionName = functionName;
        this.functionParams = functionParams;
    }
}
