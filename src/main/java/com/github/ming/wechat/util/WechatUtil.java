package com.github.ming.wechat.util;

/**
 * WechatUtil
 *
 * @author : Liu Zeming
 * @date : 2018-12-29 00:53
 */
public class WechatUtil {

    /**
     * 简体
     */
    private static final String ZH_CN = "zh_CN";

    /**
     * 繁体
     */
    private static final String ZH_TW = "zh_TW";

    /**
     * 英文
     */
    private static final String EN = "en";


    /**
     * 标准化lang参数值
     *
     * @param lang 待标准化lang参数
     * @return 标准化后的值
     */
    public static String standardLang(String lang) {
        if (!StringUtil.isBlank(lang)) {
            if (ZH_CN.equalsIgnoreCase(lang)) {
                return ZH_CN;
            } else if (ZH_TW.equalsIgnoreCase(lang)) {
                return ZH_TW;
            } else if (EN.equalsIgnoreCase(lang)) {
                return EN;
            }
        }
        return ZH_CN;
    }
}
