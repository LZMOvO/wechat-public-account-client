package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.github.ming.wechat.util.HttpUtil;

/**
 * WechatRequest
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 19:12
 */
class WechatRequest {

    static String get(String url, WechatCredentialHolder credentialHolder) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(replaceAccessToken(url, credentialHolder.getAccessToken(refreshToken)));
            if (!WechatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static <T> String post(String url, T t, WechatCredentialHolder credentialHolder) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(replaceAccessToken(url, credentialHolder.getAccessToken(refreshToken)),
                    JSON.toJSONString(t));
            if (!WechatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static String upload() {

        return null;
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
