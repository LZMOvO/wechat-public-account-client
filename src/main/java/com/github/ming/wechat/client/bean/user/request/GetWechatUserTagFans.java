package com.github.ming.wechat.client.bean.user.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取标签下粉丝列表
 *
 * @author ZM
 * @date : 2018-12-27 01:49
 */
public class GetWechatUserTagFans {

    /**
     * 标签id
     */
    @JSONField(name = "tagid")
    private int tagId;

    /**
     * 第一个拉取的OPENID，不填默认从头开始拉取
     */
    @JSONField(name = "next_openid")
    private String nextOpenId;

    public GetWechatUserTagFans(int tagId, String nextOpenId) {
        this.tagId = tagId;
        this.nextOpenId = nextOpenId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }
}
