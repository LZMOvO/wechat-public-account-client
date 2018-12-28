package com.github.ming.wechat.client.bean.user.response;

import com.github.ming.wechat.client.bean.user.WechatUserTag;

import java.util.List;

/**
 * 公众号已创建的标签
 *
 * @author : Liu Zeming
 * @date : 2018-12-20 23:54
 */
public class WechatUserTagListResult {

    private List<WechatUserTag> tags;

    public List<WechatUserTag> getTags() {
        return tags;
    }

    public void setTags(List<WechatUserTag> tags) {
        this.tags = tags;
    }
}
