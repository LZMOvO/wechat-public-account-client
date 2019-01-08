package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.credential.WechatAccessToken;
import com.github.ming.wechat.client.bean.credential.wrapper.WechatAccessTokenWrapper;
import com.github.ming.wechat.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * WechatCredentialHolder
 *
 * @author : Liu Zeming
 * @date : 2019-01-02 22:42
 */
public class WechatCredentialHolder {

    private static final Logger logger = LoggerFactory.getLogger(WechatCredentialHolder.class);

    /**
     * 第三方用户唯一凭证
     */
    private String appId;

    /**
     * 第三方用户唯一凭证密钥，即appsecret
     */
    private String appSecret;

    /**
     * 微信access_token超时重试次数
     */
    private int accessTokenTimeoutRetry;

    /**
     * 属于此appId的accessToken
     */
    private WechatAccessTokenWrapper accessToken;

    private ReentrantLock lock;

    public WechatCredentialHolder(String appId, String appSecret, int accessTokenTimeoutRetry) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.accessTokenTimeoutRetry = accessTokenTimeoutRetry + 1;
        lock = new ReentrantLock();
    }

    /**
     * 项目内获取当前可用的accessdToken
     *
     * @param refreshToken 是否重新获取accessToken true=是；false=否
     * @return
     */
    String getAccessToken(boolean refreshToken) {
        if (this.accessToken == null || refreshToken || System.currentTimeMillis() / 1000 -
                this.accessToken.getCreateTime() >= this.accessToken.getExpiresIn()) {
            this.resetAccessToken();
        }
        return accessToken.getAccessToken();
    }

    /**
     * 重置客户端内部使用的accessToken
     */
    private void resetAccessToken() {
        if (lock.tryLock()) {
            try {
                this.accessToken = new WechatAccessTokenWrapper(this.accessTokenGet());
                logger.info("重置了access_token");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            while (lock.isLocked()) {
                if (!lock.isLocked()) {
                    logger.info("啊~我发现有人刷新完token了");
                    return;
                }
            }
        }
    }

    /**
     * 获取accessToken
     */
    private WechatAccessToken accessTokenGet() {
        String result = HttpUtil.get(WechatApiUrls.ACCESS_TOKEN_URL.replace("APPID", this.appId)
                .replace("APPSECRET", this.appSecret));
        return WechatResponse.result2Bean(result, WechatAccessToken.class);
    }

    int getAccessTokenTimeoutRetry() {
        return accessTokenTimeoutRetry;
    }
}
