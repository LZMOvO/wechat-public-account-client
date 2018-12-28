package com.github.ming.wechat.client.bean.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 接口请求错误信息
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 04:15
 */
public class ErrorInfo {

    @JSONField(name = "errcode")
    private Integer errorCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
