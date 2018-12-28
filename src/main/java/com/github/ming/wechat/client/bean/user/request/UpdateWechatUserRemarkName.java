package com.github.ming.wechat.client.bean.user.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 设置用户备注名
 *
 * @author : Liu Zeming
 * @date : 2018-12-28 23:58
 */
public class UpdateWechatUserRemarkName {

    @JSONField(name = "openid")
    private String openId;

    @JSONField(name = "remark")
    private String remarkName;

    public UpdateWechatUserRemarkName(String openId, String remarkName) {
        this.openId = openId;
        this.remarkName = remarkName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }
}
