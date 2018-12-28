package com.github.ming.wechat.client.bean.user.request;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * BatchSetUserTag
 *
 * @author : Liu Zeming
 * @date : 2018-12-27 02:41
 */
public class BatchOperateWechatUserTag {

    /**
     * 标签id
     */
    @JSONField(name = "tagid")
    private int tagId;

    /**
     * 设置标签的粉丝列表
     */
    @JSONField(name = "openid_list")
    private List<String> openIdList;

    public BatchOperateWechatUserTag(int tagId, List<String> openIdList) {
        this.tagId = tagId;
        this.openIdList = openIdList;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public List<String> getOpenIdList() {
        return openIdList;
    }

    public void setOpenIdList(List<String> openIdList) {
        this.openIdList = openIdList;
    }
}
