package com.github.ming.wechat.client.bean.user.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 用户身上的标签列表
 *
 * @author : Liu Zeming
 * @date : 2018-12-28 22:42
 */
public class WechatUserTagsForUserResult {

    @JSONField(name = "tagid_list")
    private List<Integer> tagIdList;

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }
}
