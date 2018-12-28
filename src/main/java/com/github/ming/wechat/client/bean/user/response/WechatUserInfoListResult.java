package com.github.ming.wechat.client.bean.user.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.ming.wechat.client.bean.user.WechatUser;

import java.util.List;

/**
 * WechatUserInfoResult
 *
 * @author : Liu Zeming
 * @date : 2018-12-29 00:51
 */
public class WechatUserInfoListResult {

    @JSONField(name = "user_info_list")
    private List<WechatUser> userInfoList;

    public List<WechatUser> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<WechatUser> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
