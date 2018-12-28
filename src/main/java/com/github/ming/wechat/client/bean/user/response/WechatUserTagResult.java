package com.github.ming.wechat.client.bean.user.response;

import com.github.ming.wechat.client.bean.user.WechatUserTag;

/**
 * 创建标签
 *
 * @author : Liu Zeming
 * @date : 2018-12-20 03:15
 */
public class WechatUserTagResult {

    private WechatUserTag tag;

    public WechatUserTag getTag() {
        return tag;
    }

    public void setTag(WechatUserTag tag) {
        this.tag = tag;
    }
}
