package com.wzn.libaray.event;

/**
 * Created by Wind_Fantasy on 16/4/8.
 * 用于JS调用NATIVE时的事件
 */
public class BridgeModel {
    public String json;

    public BridgeModel(String json) {
        this.json = json;
    }
}
