package com.github.ming.wechat.msgevent.msgtype;

/**
 * 微信消息回复类型（微信消息中MsgType的值）
 *
 * @author : Liu Zeming
 * @date : 2018-12-31 21:45
 */
public enum WechatReplyType {

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
     * 音乐消息
     */
    MUSIC("music"),

    /**
     * 图文消息
     */
    NEWS("news");

    WechatReplyType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public static boolean findMsgTpye(String type) {
        for (WechatReplyType wechatMsgType : values()) {
            if (wechatMsgType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

}
