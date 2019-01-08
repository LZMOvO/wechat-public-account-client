package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.github.ming.wechat.util.HttpUtil;

import java.io.File;

/**
 * WechatRequest
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 19:12
 */
class WechatRequest {

    static String get(String url, WechatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.get(url.replace("ACCESS_TOKEN", credentialHolder.getAccessToken(false)));
            } else {
                result = HttpUtil.get(replaceAccessToken(url, credentialHolder.getAccessToken(true)));
            }
            if (!WechatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static <T> String post(String url, T t, WechatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.post(url.replace("ACCESS_TOKEN", credentialHolder.getAccessToken(false)),
                        JSON.toJSONString(t));
            } else {
                result = HttpUtil.post(replaceAccessToken(url, credentialHolder.getAccessToken(true)),
                        JSON.toJSONString(t));
            }
            if (!WechatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static String upload(String url, File file, WechatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.uploadForWechat(url.replace("ACCESS_TOKEN", credentialHolder.getAccessToken(false)), file);
            } else {
                result = HttpUtil.uploadForWechat(replaceAccessToken(url, credentialHolder.getAccessToken(true)), file);
            }
            if (!WechatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    private static String replaceAccessToken(String url, String accessToken) {
        StringBuilder urlBuilder = new StringBuilder(url);
        int start = urlBuilder.indexOf("access_token=") + 13;
        int end = urlBuilder.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return urlBuilder.replace(start, end, accessToken).toString();
    }

}
