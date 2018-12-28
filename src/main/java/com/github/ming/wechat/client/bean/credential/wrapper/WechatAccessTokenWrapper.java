package com.github.ming.wechat.client.bean.credential.wrapper;

import com.github.ming.wechat.client.bean.credential.WechatAccessToken;

/**
 * WechatAccessTokenWrapper
 *
 * @author : Liu Zeming
 * @date : 2018-12-17 21:23
 */
public class WechatAccessTokenWrapper extends WechatAccessToken {

    /**
     * 获取accessToken时的时间戳，单位：秒
     */
    private long createTime;

    public WechatAccessTokenWrapper(WechatAccessToken accessToken) {
        this.setAccessToken(accessToken.getAccessToken());
        this.setExpiresIn(accessToken.getExpiresIn());
        this.setCreateTime(System.currentTimeMillis() / 1000 - 5);
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
