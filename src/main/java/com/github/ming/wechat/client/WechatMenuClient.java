package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.menu.WechatMenuButton;
import com.github.ming.wechat.client.bean.menu.WechatMenuButtonGroup;
import com.github.ming.wechat.client.bean.menu.response.WechatMenusResult;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.client.handler.WechatResultHandler;
import com.github.ming.wechat.util.HttpUtil;
import com.github.ming.wechat.util.StringUtil;

import java.util.List;

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
        ErrorInfo errorInfo = createMenuPost(menuButtonList);
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 自定义菜单创建 实际接口调用
     * accessToken超时重试
     *
     * @param menuButtonList 创建菜单参数
     */
    private ErrorInfo createMenuPost(List<WechatMenuButton> menuButtonList) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        JSONObject params = new JSONObject();
        params.put("button", menuButtonList);
        do {
            result = HttpUtil.post(WechatApiUrls.CREATE_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), params.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        params = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
    }

    /**
     * 自定义菜单查询
     *
     * @return 标签列表
     */
    public WechatMenusResult menus() throws WechatException {
        return menusGet();
    }

    /**
     * 自定义菜单查询 实际接口调用
     * accessToken超时重试
     *
     * @return 按照微信返回格式创建的list
     */
    private WechatMenusResult menusGet() {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(WechatApiUrls.GET_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatMenusResult.class);
    }

    /**
     * 自定义菜单删除
     * 删除所有菜单
     *
     * @return true=成功
     */
    public boolean deleteMenus() throws WechatException {
        ErrorInfo errorInfo = deleteMenusGet();
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 自定义菜单删除 接口实际调用
     * accessToken超时重试
     *
     * @return 请求结果
     */
    private ErrorInfo deleteMenusGet() {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(WechatApiUrls.DELETE_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        ErrorInfo errorInfo = createConditionalMenuPost(wechatMenuButtonGroup);
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 创建个性化菜单 实际接口调用
     * accessToken超时重试
     *
     * @param wechatMenuButtonGroup 个性化菜单参数
     */
    private ErrorInfo createConditionalMenuPost(WechatMenuButtonGroup wechatMenuButtonGroup) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.CREATE_CONDITIONAL_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(wechatMenuButtonGroup));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
    }


    /**
     * 删除个性化菜单
     *
     * @return true=成功
     */
    public boolean deleteConditionalMenus(int menuId) throws WechatException {
        ErrorInfo errorInfo = deleteConditionalMenusPost(menuId);
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 删除个性化菜单 接口实际调用
     * accessToken超时重试
     *
     * @return 请求结果
     */
    private ErrorInfo deleteConditionalMenusPost(int menuId) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        JSONObject params = new JSONObject(3);
        params.put("menuid", menuId);
        do {
            result = HttpUtil.post(WechatApiUrls.DELETE_CONDITIONAL_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), params.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        params = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        return tryMatchMenuPost(userId);
    }

    /**
     * 测试个性化菜单匹配结果 接口实际调用
     * accessToken超时重试
     *
     * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号
     * @return 请求结果
     */
    private WechatMenuButtonGroup tryMatchMenuPost(String userId) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        JSONObject params = new JSONObject(3);
        params.put("user_id", userId);
        do {
            result = HttpUtil.post(WechatApiUrls.TRY_MATCH_MENU_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), params.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        params = null;
        return WechatResultHandler.result2Bean(result, WechatMenuButtonGroup.class);
    }

}
