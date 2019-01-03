package com.github.ming.wechat.event.msgtype;

/**
 * 微信事件类型
 *
 * @author : Liu Zeming
 * @date : 2018-12-31 23:05
 */
public enum WechatEventType {

    /**
     * 关注事件（订阅）
     */
    SUBSCRIBE("subscribe"),

    /**
     * 取消关注事件（取消订阅）
     */
    UNSUBSCRIBE("unsubscribe"),

    /**
     * 已关注时的事件推送
     */
    SCAN("SCAN"),

    /**
     * 上报地理位置事件
     */
    LOCATION("LOCATION"),

    /**
     * 点击菜单拉取消息时的事件
     */
    CLICK("CLICK"),

    /**
     * 点击菜单跳转链接时的事件
     */
    VIEW("VIEW"),

    ;

    WechatEventType(String event) {
        this.event = event;
    }

    private String event;

    public String getEvent() {
        return event;
    }

    public static boolean findMsgEvent(String event) {
        for (WechatEventType wechatEventType : values()) {
            if (wechatEventType.getEvent().equals(event)) {
                return true;
            }
        }
        return false;
    }
}
