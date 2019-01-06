package com.github.ming.wechat.client.apiurl;

/**
 * 微信公众号接口地址
 *
 * @author : Liu Zeming
 * @date : 2018-12-17 19:34
 */
public class WechatApiUrls {

    /**
     * 自定义菜单创建接口
     */
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 自定义菜单查询接口
     */
    public static final String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    /**
     * 自定义菜单删除接口
     */
    public static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * 测试个性化菜单匹配结果
     */
    public static final String TRY_MATCH_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN";

    /**
     * 创建个性化菜单
     */
    public static final String CREATE_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";

    /**
     * 删除个性化菜单
     */
    public static final String DELETE_CONDITIONAL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";

    /**
     * 获取access_token接口地址
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 创建标签
     */
    public static final String CREATE_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";

    /**
     * 获取公众号已创建的标签
     */
    public static final String GET_USER_TAGS_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";

    /**
     * 编辑标签
     */
    public static final String UPDATE_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";

    /**
     * 删除标签
     */
    public static final String DELETE_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";

    /**
     * 获取标签下粉丝列表
     */
    public static final String USER_TAGS_FANS_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";

    /**
     * 批量为用户打标签
     */
    public static final String BATCH_SET_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";

    /**
     * 批量为用户取消标签
     */
    public static final String BATCH_CANCEL_USER_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";

    /**
     * 获取用户身上的标签列表
     */
    public static final String GET_USER_TAGS_FOR_USER_URL = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";

    /**
     * 设置用户备注名
     */
    public static final String UPDATE_USER_REMARK_NAME_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";

    /**
     * 获取用户基本信息（包括UnionID机制）接口地址
     */
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=LANG";

    /**
     * 批量获取用户基本信息
     */
    public static final String BATCH_GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";

    /**
     * 获取用户列表
     */
    public static final String USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

    /**
     * 获取公众号的黑名单列表
     */
    public static final String BLACKLIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN";

    /**
     * 拉黑用户（批量）
     */
    public static final String BATCH_BLACK_USER_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN";

    /**
     * 取消拉黑用户（批量）
     */
    public static final String BATCH_UNBLACK_USER_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN";
}
