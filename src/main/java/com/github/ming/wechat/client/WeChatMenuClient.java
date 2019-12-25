package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WeChatApiUrls;
import com.github.ming.wechat.client.base.WeChatClientCredential;
import com.github.ming.wechat.client.base.WeChatCredentialHolder;
import com.github.ming.wechat.client.base.WeChatRequest;
import com.github.ming.wechat.client.base.WeChatResponse;
import com.github.ming.wechat.client.bean.menu.WeChatMenuButton;
import com.github.ming.wechat.client.bean.menu.WeChatMenuButtonGroup;
import com.github.ming.wechat.client.bean.menu.response.WeChatMenusResult;
import com.github.ming.wechat.client.exception.WeChatException;
import com.github.ming.wechat.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义菜单
 *
 * @author ZM
 * @date : 2019-01-04 04:17
 */
public class WeChatMenuClient extends WeChatClientCredential {

    public WeChatMenuClient(WeChatCredentialHolder credentialHolder) {
        this.setCredentialHolder(credentialHolder);
    }

    /*---- 自定义菜单 ----*/

    /**
     * 自定义菜单创建
     * 自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单
     *
     * @param menuButtonList 创建菜单参数
     * @return true=成功
     */
    public boolean createMenu(List<WeChatMenuButton> menuButtonList) throws WeChatException {
        if (menuButtonList == null || menuButtonList.size() == 0) {
            throw new WeChatException("创建菜单的内容为空");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("button", menuButtonList);
        String result = WeChatRequest.post(WeChatApiUrls.CREATE_MENU_URL, params, this.getCredentialHolder());
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 自定义菜单查询
     *
     * @return 标签列表
     */
    public WeChatMenusResult menus() throws WeChatException {
        String result = WeChatRequest.get(WeChatApiUrls.GET_MENU_URL, this.getCredentialHolder());
        return WeChatResponse.result2Bean(result, WeChatMenusResult.class);
    }

    /**
     * 自定义菜单删除
     * 删除所有菜单
     *
     * @return true=成功
     */
    public boolean deleteMenus() throws WeChatException {
        String result = WeChatRequest.get(WeChatApiUrls.DELETE_MENU_URL, this.getCredentialHolder());
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /*---- 个性化菜单 ----*/

    /**
     * 创建个性化菜单
     *
     * @param wechatMenuButtonGroup 个性化菜单参数
     * @return true=成功
     */
    public boolean createConditionalMenu(WeChatMenuButtonGroup wechatMenuButtonGroup) throws WeChatException {
        if (wechatMenuButtonGroup == null) {
            throw new WeChatException("创建菜单的内容为空");
        }
        String result = WeChatRequest.post(WeChatApiUrls.CREATE_CONDITIONAL_MENU_URL, wechatMenuButtonGroup, this.getCredentialHolder());
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 删除个性化菜单
     *
     * @return true=成功
     */
    public boolean deleteConditionalMenus(int menuId) throws WeChatException {
        Map<String, Object> params = new HashMap<>(3);
        params.put("menuid", menuId);
        String result = WeChatRequest.post(WeChatApiUrls.DELETE_CONDITIONAL_MENU_URL, params, this.getCredentialHolder());
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号
     * @return 请求结果
     */
    public WeChatMenuButtonGroup tryMatchMenu(String userId) throws WeChatException {
        if (StringUtil.isBlank(userId)) {
            throw new WeChatException("userId为空");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("user_id", userId);
        String result = WeChatRequest.post(WeChatApiUrls.TRY_MATCH_MENU_URL, params, this.getCredentialHolder());
        return WeChatResponse.result2Bean(result, WeChatMenuButtonGroup.class);
    }
}
