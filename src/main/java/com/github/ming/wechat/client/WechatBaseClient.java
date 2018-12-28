package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.credential.WechatAccessToken;
import com.github.ming.wechat.client.bean.credential.wrapper.WechatAccessTokenWrapper;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.config.WechatConfig;
import com.github.ming.wechat.client.credential.WechatCredential;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.client.returncode.WechatReturnCode;
import com.github.ming.wechat.util.HttpUtil;
import com.github.ming.wechat.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * WechatBaseClient
 *
 * @author : Liu Zeming
 * @date : 2018-12-16 23:04
 */
class WechatBaseClient {

    private static final Logger logger = LoggerFactory.getLogger(WechatBaseClient.class);

    /**
     * 从请求结果中判断请求失败的标识
     */
    private static final String RESULT_FAULT_FLAG = "errcode";

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 项目内获取当前可用的accessdToken
     *
     * @param refreshToken 是否重新获取accessToken true=是；false=否
     * @return
     */
    String getAccessToken(boolean refreshToken) {
        if (WechatCredential.accessToken == null || refreshToken || System.currentTimeMillis() / 1000 -
                WechatCredential.accessToken.getCreateTime() >= WechatCredential.accessToken.getExpiresIn()) {
            this.resetAccessToken();
        }
        return WechatCredential.accessToken.getAccessToken();
    }

    /**
     * 重置客户端内部使用的accessToken
     */
    private void resetAccessToken() {
        if (lock.tryLock()) {
            try {
                WechatCredential.accessToken = new WechatAccessTokenWrapper(this.accessTokenGet());
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
        String result = HttpUtil.get(WechatApiUrls.ACCESS_TOKEN_URL.replace("APPID", WechatConfig.appId)
                .replace("APPSECRET", WechatConfig.appSecret));
        return this.result2Bean(result, WechatAccessToken.class);
    }

    /**
     * 微信api返回的结果转bean
     *
     * @param wechatResult 请求返回的结果
     * @param clazz        要转成的bean的clazz
     * @param <T>          要转成的bean
     * @return
     * @throws WechatException 微信错误异常信息
     */
    <T> T result2Bean(String wechatResult, Class<T> clazz) throws WechatException {
        if (StringUtil.isBlank(wechatResult)) {
            throw new WechatException("微信api请求结果为空");
        }
        if (wechatResult.contains(RESULT_FAULT_FLAG)) {
            ErrorInfo errorInfo = JSON.parseObject(wechatResult, ErrorInfo.class);
            if (errorInfo.getErrorCode() != WechatReturnCode.OK.getCode()) {
                throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
            }
        }
        return JSON.parseObject(wechatResult, clazz);
    }

    /**
     * 根据微信api请求错误信息的错误码判断是否为accessToken超时错误
     *
     * @param result 微信请求失败的错误码
     * @return true=是accessToken超时；false=不是
     */
    boolean judgeAccessTokenTimeout(String result) {
        if (!StringUtil.isBlank(result) && result.contains(RESULT_FAULT_FLAG)) {
            ErrorInfo errorInfo = JSON.parseObject(result, ErrorInfo.class);
            return errorInfo.getErrorCode() == WechatReturnCode.IllegalAccessToken.getCode() ||
                    errorInfo.getErrorCode() == WechatReturnCode.ObtainAccessTokenError.getCode();
        } else {
            return false;
        }
    }

}
