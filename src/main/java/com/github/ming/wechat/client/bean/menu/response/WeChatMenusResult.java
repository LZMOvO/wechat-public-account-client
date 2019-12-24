package com.github.ming.wechat.client.bean.menu.response;

import com.github.ming.wechat.client.bean.menu.WeChatMenuButtonGroup;

/**
 * 菜单信息
 *
 * @author ZM
 * @date : 2019-01-04 19:05
 */
public class WeChatMenusResult {

    /**
     * 默认菜单
     */
    private WeChatMenuButtonGroup menu;

    /**
     * 个性化菜单
     */
    private WeChatMenuButtonGroup conditionalmenu;

    public WeChatMenuButtonGroup getMenu() {
        return menu;
    }

    public void setMenu(WeChatMenuButtonGroup menu) {
        this.menu = menu;
    }

    public WeChatMenuButtonGroup getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(WeChatMenuButtonGroup conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }
}
