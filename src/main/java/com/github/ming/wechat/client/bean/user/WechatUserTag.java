package com.github.ming.wechat.client.bean.user;

/**
 * WechatUserTag
 *
 * @author : Liu Zeming
 * @date : 2018-12-21 00:28
 */
public class WechatUserTag {

    /**
     * 标签id，由微信分配
     */
    private Integer id;

    /**
     * 标签名，UTF8编码
     */
    private String name;

    /**
     * 此标签下粉丝数
     */
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
