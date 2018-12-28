package com.github.ming.wechat.client.apiurl;

/**
 * 微信公众号接口地址
 *
 * @author : Liu Zeming
 * @date : 2018-12-17 19:34
 */
public class WechatApiUrls {

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
}
