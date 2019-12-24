package com.github.ming.wechat.client.bean.user.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 标签下粉丝列表
 *
 * @author ZM
 * @date : 2018-12-27 01:33
 */
public class WeChatUserTagFansResult {

    /**
     * 这次获取的粉丝数量
     */
    private int count;

    /**
     * 粉丝列表
     */
    private UserTagFans data;

    /**
     * 拉取列表最后一个用户的openid
     */
    @JSONField(name = "next_openid")
    private String nextOpenId;

    public class UserTagFans {

        @JSONField(name = "openid")
        private List<String> openId;

        public List<String> getOpenId() {
            return openId;
        }

        public void setOpenId(List<String> openId) {
            this.openId = openId;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserTagFans getData() {
        return data;
    }

    public void setData(UserTagFans data) {
        this.data = data;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }
}
