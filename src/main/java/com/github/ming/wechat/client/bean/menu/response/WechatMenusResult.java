package com.github.ming.wechat.client.bean.menu.response;

import com.github.ming.wechat.client.bean.menu.WechatMenuButtonGroup;

/**
 * 菜单信息
 *
 * @author : Liu Zeming
 * @date : 2019-01-04 19:05
 */
public class WechatMenusResult {

    /**
     * 默认菜单
     */
    private WechatMenuButtonGroup menu;

    /**
     * 个性化菜单
     */
    private WechatMenuButtonGroup conditionalmenu;

    public WechatMenuButtonGroup getMenu() {
        return menu;
    }

    public void setMenu(WechatMenuButtonGroup menu) {
        this.menu = menu;
    }

    public WechatMenuButtonGroup getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(WechatMenuButtonGroup conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }
}
