package com.github.ming.wechat.client.bean.credential;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信公众号全局唯一接口调用凭据的model类
 * <p>
 * 官方说明：access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
 * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 02:07
 */
public class WechatAccessToken {

    /**
     * 获取到的凭证
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒
     */
    @JSONField(name = "expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
