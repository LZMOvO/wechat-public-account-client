package com.github.ming.wechat.client.config;

/**
 * WechatConfig
 *
 * @author : Liu Zeming
 * @date : 2018-12-16 14:24
 */
public class WechatConfig {

    /**
     * 第三方用户唯一凭证
     */
    public static String appId;

    /**
     * 第三方用户唯一凭证密钥，即appsecret
     */
    public static String appSecret;

    /**
     * 微信access_token超时重试次数
     */
    public static int accessTokenTimeoutRetry = 1;

}
