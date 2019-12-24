package com.github.ming.wechat.client.type.qrcode;

/**
 * 二维码类型
 *
 * @author ZM
 * @date : 2019-02-16 14:49
 */
public enum ActionName {

    /**
     * 临时的整型参数值
     */
    QR_SCENE("QR_SCENE"),

    /**
     * 临时的字符串参数值
     */
    QR_STR_SCENE("QR_STR_SCENE"),

    /**
     * 永久的整型参数值
     */
    QR_LIMIT_SCENE("QR_LIMIT_SCENE"),

    /**
     * 永久的字符串参数值
     */
    QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE");

    ActionName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

}
