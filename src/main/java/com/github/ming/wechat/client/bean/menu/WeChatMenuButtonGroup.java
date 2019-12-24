package com.github.ming.wechat.client.bean.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * WeChatMenuButtonGroup
 *
 * @author ZM
 * @date : 2019-01-05 10:42
 */
public class WeChatMenuButtonGroup {

    /**
     * 默认菜单
     */
    private List<WeChatMenuButton> button;

    /**
     * 菜单匹配规则
     */
    private WeChatMenuMatchrule matchrule;

    /**
     * menuid
     */
    @JSONField(name = "menuid")
    private Integer menuId;

    public List<WeChatMenuButton> getButton() {
        return button;
    }

    public void setButton(List<WeChatMenuButton> button) {
        this.button = button;
    }

    public WeChatMenuMatchrule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(WeChatMenuMatchrule matchrule) {
        this.matchrule = matchrule;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
