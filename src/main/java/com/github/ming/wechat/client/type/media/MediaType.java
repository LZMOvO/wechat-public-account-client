package com.github.ming.wechat.client.type.media;

/**
 * 上传素材类型
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 11:20
 */
public enum MediaType {

    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 语音
     */
    VOICE("voice"),

    /**
     * 视频
     */
    VIDEO("video"),

    /**
     * 缩略图
     */
    THUMB("thumb"),
    ;

    MediaType(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public static boolean findType(String type) {
        for (MediaType mediaType : values()) {
            if (mediaType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
