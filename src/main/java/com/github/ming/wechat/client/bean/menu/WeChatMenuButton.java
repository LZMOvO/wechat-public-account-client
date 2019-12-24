package com.github.ming.wechat.client.bean.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 公众号菜单一级按钮
 *
 * @author ZM
 * @date : 2019-01-04 14:49
 */
public class WeChatMenuButton {

    /**
     * 菜单的响应动作类型
     */
    private String type;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节
     */
    private String name;

    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String key;

    /**
     * 网页 链接，用户点击菜单可打开链接，不超过1024字节。
     * type为miniprogram时，不支持小程序的老版本客户端将打开本url
     */
    private String url;

    /**
     * 调用新增永久素材接口返回的合法media_id
     */
    @JSONField(name = "media_id")
    private String mediaId;

    /**
     * 小程序的appid（仅认证公众号可配置）
     */
    private String appid;

    /**
     * 小程序的页面路径
     */
    @JSONField(name = "pagepath")
    private String pagePath;

    /**
     * 二级菜单数组，个数应为1~5个
     */
    @JSONField(name = "sub_button")
    private List<WeChatMenuButton> subButton;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public List<WeChatMenuButton> getSubButton() {
        return subButton;
    }

    public void setSubButton(List<WeChatMenuButton> subButton) {
        this.subButton = subButton;
    }
}
