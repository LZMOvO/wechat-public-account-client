package com.github.ming.wechat.client.bean.user.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.ming.wechat.client.bean.user.WeChatUser;

import java.util.List;

/**
 * WeChatUserInfoListResult
 *
 * @author ZM
 * @date : 2018-12-29 00:51
 */
public class WeChatUserInfoListResult {

    @JSONField(name = "user_info_list")
    private List<WeChatUser> userInfoList;

    public List<WeChatUser> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<WeChatUser> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
