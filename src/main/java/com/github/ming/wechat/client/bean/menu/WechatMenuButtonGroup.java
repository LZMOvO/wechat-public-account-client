package com.github.ming.wechat.client.bean.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * WechatMenuButtonGroup
 *
 * @author : Liu Zeming
 * @date : 2019-01-05 10:42
 */
public class WechatMenuButtonGroup {

    /**
     * 默认菜单
     */
    private List<WechatMenuButton> button;

    /**
     * 菜单匹配规则
     */
    private WechatMenuMatchrule matchrule;

    /**
     * menuid
     */
    @JSONField(name = "menuid")
    private Integer menuId;

    public List<WechatMenuButton> getButton() {
        return button;
    }

    public void setButton(List<WechatMenuButton> button) {
        this.button = button;
    }

    public WechatMenuMatchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(WechatMenuMatchrule matchrule) {
        this.matchrule = matchrule;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
