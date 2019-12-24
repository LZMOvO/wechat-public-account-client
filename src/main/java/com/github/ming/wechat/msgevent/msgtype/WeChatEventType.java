package com.github.ming.wechat.msgevent.msgtype;

/**
 * 微信事件类型
 *
 * @author ZM
 * @date : 2018-12-31 23:05
 */
public enum WeChatEventType {

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

    /**
     * 扫码推事件的事件推送
     */
    SCANCODE_PUSH("scancode_push"),

    /**
     * 扫码推事件且弹出“消息接收中”提示框的事件推送
     */
    SCANCODE_WAITMSG("scancode_waitmsg"),

    /**
     * 弹出系统拍照发图的事件推送
     */
    PIC_SYSPHOTO("pic_sysphoto"),

    /**
     * 弹出拍照或者相册发图的事件推送
     */
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),

    /**
     * 弹出微信相册发图器的事件推送
     */
    PIC_WEIXIN("pic_weixin"),

    /**
     * 弹出地理位置选择器的事件推送
     */
    LOCATION_SELECT("location_select"),

    /**
     * 点击菜单跳转小程序的事件推送
     */
    VIEW_MINIPROGRAM("view_miniprogram"),
    ;

    WeChatEventType(String event) {
        this.event = event;
    }

    private String event;

    public String getEvent() {
        return event;
    }

    public static boolean findMsgEvent(String event) {
        for (WeChatEventType wechatEventType : values()) {
            if (wechatEventType.getEvent().equals(event)) {
                return true;
            }
        }
        return false;
    }
}
