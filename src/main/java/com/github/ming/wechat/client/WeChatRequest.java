package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.github.ming.wechat.client.bean.media.WeChatMaterialVideoDesc;
import com.github.ming.wechat.util.HttpUtil;

import java.io.File;

/**
 * WeChatRequest
 *
 * @author ZM
 * @date : 2019-01-07 19:12
 */
class WeChatRequest {

    static String get(String url, WeChatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.get(url.replace("ACCESS_TOKEN", credentialHolder.getAccessToken(false)));
            } else {
                result = HttpUtil.get(replaceAccessToken(url, credentialHolder.getAccessToken(true)));
            }
            if (!WeChatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static <T> String post(String url, T t, WeChatCredentialHolder credentialHolder) {
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
            if (!WeChatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static String upload(String url, File file, WeChatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.uploadForWechat(url.replace("ACCESS_TOKEN", credentialHolder.getAccessToken(false)), file);
            } else {
                result = HttpUtil.uploadForWechat(replaceAccessToken(url, credentialHolder.getAccessToken(true)), file);
            }
            if (!WeChatResponse.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return result;
    }

    static String uploadMaterialVideo(String url, File file, WeChatMaterialVideoDesc desc, WeChatCredentialHolder credentialHolder) {
        String result;
        int times = 0;
        do {
            if (times == 0) {
                result = HttpUtil.uploadForWechatMaterialVideo(url.replace("ACCESS_TOKEN",
                        credentialHolder.getAccessToken(false)), file, JSON.toJSONString(desc));
            } else {
                result = HttpUtil.uploadForWechatMaterialVideo(url.replace("ACCESS_TOKEN",
                        credentialHolder.getAccessToken(true)), file, JSON.toJSONString(desc));
            }
            if (!WeChatResponse.judgeAccessTokenTimeout(result)) {
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