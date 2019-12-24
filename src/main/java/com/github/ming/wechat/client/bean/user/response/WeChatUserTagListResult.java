package com.github.ming.wechat.client.bean.user.response;

import com.github.ming.wechat.client.bean.user.WeChatUserTag;

import java.util.List;

/**
 * 公众号已创建的标签
 *
 * @author ZM
 * @date : 2018-12-20 23:54
 */
public class WeChatUserTagListResult {

    private List<WeChatUserTag> tags;

    public List<WeChatUserTag> getTags() {
        return tags;
    }

    public void setTags(List<WeChatUserTag> tags) {
        this.tags = tags;
    }
}
