package com.github.ming.wechat.client.exception;

/**
 * WechatException
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 16:36
 */
public class WechatException extends RuntimeException {

    private static final long serialVersionUID = 1351146572460581551L;

    private Integer errorCode;

    public WechatException(String errMsg) {
        super(errMsg);
    }

    public WechatException(int errorCode, String errMsg) {
        super("错误码：" + errorCode + " , 错误信息：" + errMsg);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
