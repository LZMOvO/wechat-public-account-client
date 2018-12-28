package com.github.ming.wechat.client.bean.user.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量获取用户基本信息
 *
 * @author : Liu Zeming
 * @date : 2018-12-29 00:30
 */
public class BatchGetWechatUserInfo {

    @JSONField(name = "user_list")
    private List<OpenIdAndLang> userList;

    public BatchGetWechatUserInfo(String lang, List<String> openIdList) {
        userList = new ArrayList<>(openIdList.size());
        for (String openId : openIdList) {
            userList.add(new OpenIdAndLang(openId, lang));
        }
    }

    private class OpenIdAndLang {

        @JSONField(name = "openid")
        private String openId;

        private String lang;

        OpenIdAndLang(String openId, String lang) {
            this.openId = openId;
            this.lang = lang;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }

    public List<OpenIdAndLang> getUserList() {
        return userList;
    }

    public void setUserList(List<OpenIdAndLang> userList) {
        this.userList = userList;
    }
}
