package com.github.ming.wechat.client.exception;

/**
 * WeChatException
 *
 * @author ZM
 * @date : 2018-12-14 16:36
 */
public class WeChatException extends RuntimeException {

    private static final long serialVersionUID = 1351146572460581551L;

    private Integer errorCode;

    public WeChatException(String errMsg) {
        super(errMsg);
    }

    public WeChatException(int errorCode, String errMsg) {
        super("错误码：" + errorCode + " , 错误信息：" + errMsg);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
