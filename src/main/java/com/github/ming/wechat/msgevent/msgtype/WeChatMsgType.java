package com.github.ming.wechat.msgevent.msgtype;

/**
 * 微信消息类型（微信消息中MsgType的值）
 *
 * @author ZM
 * @date : 2018-12-31 21:45
 */
public enum WeChatMsgType {

    /**
     * 文本消息
     */
    TEXT("text"),

    /**
     * 图片消息
     */
    IMAGE("image"),

    /**
     * 语音消息
     */
    VOICE("voice"),

    /**
     * 视频消息
     */
    VIDEO("video"),

    /**
     * 小视频消息
     */
    SHORTVIDEO("shortvideo"),

    /**
     * 地理位置消息
     */
    LOCATION("location"),

    /**
     * 连接消息
     */
    LINK("link"),

    /**
     * 事件推送消息
     */
    EVENT("event"),

    ;

    WeChatMsgType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public static boolean findMsgType(String type) {
        for (WeChatMsgType wechatMsgType : values()) {
            if (wechatMsgType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
