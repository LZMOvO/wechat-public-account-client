package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.menu.WechatMenuButton;
import com.github.ming.wechat.client.bean.menu.WechatMenuButtonGroup;
import com.github.ming.wechat.client.bean.menu.response.WechatMenusResult;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义菜单
 *
 * @author : Liu Zeming
 * @date : 2019-01-04 04:17
 */
public class WechatMenuClient {

    private WechatCredentialHolder credentialHolder;

    public WechatMenuClient(WechatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /*---- 自定义菜单 ----*/

    /**
     * 自定义菜单创建
     * 自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单
     *
     * @param menuButtonList 创建菜单参数
     * @return true=成功
     */
    public boolean createMenu(List<WechatMenuButton> menuButtonList) throws WechatException {
        if (menuButtonList == null || menuButtonList.size() == 0) {
            throw new WechatException("创建菜单的内容为空");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("button", menuButtonList);
        String result = WechatRequest.post(WechatApiUrls.CREATE_MENU_URL, params, credentialHolder);
        return WechatResponse.errorInfo2Boolean(result);
    }

    /**
     * 自定义菜单查询
     *
     * @return 标签列表
     */
    public WechatMenusResult menus() throws WechatException {
        String result = WechatRequest.get(WechatApiUrls.GET_MENU_URL, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMenusResult.class);
    }

    /**
     * 自定义菜单删除
     * 删除所有菜单
     *
     * @return true=成功
     */
    public boolean deleteMenus() throws WechatException {
        String result = WechatRequest.get(WechatApiUrls.DELETE_MENU_URL, credentialHolder);
        return WechatResponse.errorInfo2Boolean(result);
    }

    /*---- 个性化菜单 ----*/

    /**
     * 创建个性化菜单
     *
     * @param wechatMenuButtonGroup 个性化菜单参数
     * @return true=成功
     */
    public boolean createConditionalMenu(WechatMenuButtonGroup wechatMenuButtonGroup) throws WechatException {
        if (wechatMenuButtonGroup == null) {
            throw new WechatException("创建菜单的内容为空");
        }
        String result = WechatRequest.post(WechatApiUrls.CREATE_CONDITIONAL_MENU_URL, wechatMenuButtonGroup, credentialHolder);
        return WechatResponse.errorInfo2Boolean(result);
    }

    /**
     * 删除个性化菜单
     *
     * @return true=成功
     */
    public boolean deleteConditionalMenus(int menuId) throws WechatException {
        Map<String, Object> params = new HashMap<>(3);
        params.put("menuid", menuId);
        String result = WechatRequest.post(WechatApiUrls.DELETE_CONDITIONAL_MENU_URL, params, credentialHolder);
        return WechatResponse.errorInfo2Boolean(result);
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号
     * @return 请求结果
     */
    public WechatMenuButtonGroup tryMatchMenu(String userId) throws WechatException {
        if (StringUtil.isBlank(userId)) {
            throw new WechatException("userId为空");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("user_id", userId);
        String result = WechatRequest.post(WechatApiUrls.TRY_MATCH_MENU_URL, params, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMenuButtonGroup.class);
    }

}
