package com.github.ming.wechat.client.bean.user.response;

import com.github.ming.wechat.client.bean.user.WeChatUserTag;

/**
 * 创建标签
 *
 * @author ZM
 * @date : 2018-12-20 03:15
 */
public class WeChatUserTagResult {

    private WeChatUserTag tag;

    public WeChatUserTag getTag() {
        return tag;
    }

    public void setTag(WeChatUserTag tag) {
        this.tag = tag;
    }
}
