package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.exception.WeChatException;
import com.github.ming.wechat.client.returncode.WeChatErrorCode;
import com.github.ming.wechat.util.StringUtil;

/**
 * WeChatResponse
 *
 * @author ZM
 * @date : 2019-01-02 22:54
 */
class WeChatResponse {
    /**
     * 从请求结果中判断请求失败的标识
     */
    private static final String RESULT_FAULT_FLAG = "errcode";

    /**
     * 微信api返回的结果转bean
     *
     * @param wechatResult 请求返回的结果
     * @param clazz        要转成的bean的clazz
     * @param <T>          要转成的bean
     * @return
     * @throws WeChatException 微信错误异常信息
     */
    static <T> T result2Bean(String wechatResult, Class<T> clazz) throws WeChatException {
        if (StringUtil.isBlank(wechatResult)) {
            throw new WeChatException("微信api请求结果为空");
        }
        if (wechatResult.contains(RESULT_FAULT_FLAG)) {
            ErrorInfo errorInfo = JSON.parseObject(wechatResult, ErrorInfo.class);
            if (errorInfo.getErrorCode() != WeChatErrorCode.OK.getCode()) {
                throw new WeChatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
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
    static boolean judgeAccessTokenTimeout(String result) {
        if (!StringUtil.isBlank(result) && result.contains(RESULT_FAULT_FLAG)) {
            ErrorInfo errorInfo = JSON.parseObject(result, ErrorInfo.class);
            return errorInfo.getErrorCode() == WeChatErrorCode.IllegalAccessToken.getCode() ||
                    errorInfo.getErrorCode() == WeChatErrorCode.ObtainAccessTokenError.getCode() ||
                    errorInfo.getErrorCode() == WeChatErrorCode.AccessTokenOvertime.getCode();
        } else {
            return false;
        }
    }

    static boolean errorInfo2Boolean(String result) {
        ErrorInfo errorInfo = WeChatResponse.result2Bean(result, ErrorInfo.class);
        if (errorInfo.getErrorCode() == 0) {
            return true;
        } else {
            throw new WeChatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }
}
