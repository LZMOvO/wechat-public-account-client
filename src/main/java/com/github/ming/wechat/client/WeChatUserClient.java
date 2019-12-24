package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WeChatApiUrls;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.bean.user.WeChatUser;
import com.github.ming.wechat.client.bean.user.WeChatUserOpenIdList;
import com.github.ming.wechat.client.bean.user.WeChatUserTag;
import com.github.ming.wechat.client.bean.user.request.*;
import com.github.ming.wechat.client.bean.user.response.*;
import com.github.ming.wechat.client.exception.WeChatException;
import com.github.ming.wechat.client.returncode.WeChatErrorCode;
import com.github.ming.wechat.util.StringUtil;
import com.github.ming.wechat.util.WeChatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author ZM
 * @date : 2018-12-14 17:36
 */
public final class WeChatUserClient {

    private WeChatCredentialHolder credentialHolder;

    public WeChatUserClient(WeChatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /*---- 用户标签管理 ----*/

    /**
     * 创建标签
     *
     * @param name 创建的标签名字，30个字符以内
     * @return 返回创建标签信息
     */
    public WeChatUserTag createUserTag(String name) throws WeChatException {
        if (StringUtil.isBlank(name)) {
            throw new WeChatException("参数 openId 为空");
        }
        String result = WeChatRequest.post(WeChatApiUrls.CREATE_USER_TAG_URL, new CreateWechatUserTag(name), credentialHolder);
        WeChatUserTagResult wechatUserTagResult = WeChatResponse.result2Bean(result, WeChatUserTagResult.class);
        return wechatUserTagResult != null ? wechatUserTagResult.getTag() : null;
    }

    /**
     * 获取公众号已创建的标签
     *
     * @return 标签列表
     */
    public List<WeChatUserTag> userTags() throws WeChatException {
        String result = WeChatRequest.get(WeChatApiUrls.GET_USER_TAGS_URL, credentialHolder);
        WeChatUserTagListResult wechatUserTagList = WeChatResponse.result2Bean(result, WeChatUserTagListResult.class);
        return wechatUserTagList != null ? wechatUserTagList.getTags() : null;
    }

    /**
     * 编辑标签
     *
     * @param id   标签id
     * @param name 标签名
     * @return true=更新成功
     */
    public boolean updateUserTag(int id, String name) throws WeChatException {
        if (id < 0) {
            throw new WeChatException("标签id错误");
        }
        if (StringUtil.isBlank(name)) {
            throw new WeChatException("标签名为空");
        }
        String result = WeChatRequest.post(WeChatApiUrls.UPDATE_USER_TAG_URL, new UpdateWechatUserTag(id, name), credentialHolder);
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 删除标签
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，
     * 先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param id 要删除的标签id
     * @return true=更新成功
     */
    public boolean deleteUserTag(int id) throws WeChatException {
        if (id < 0) {
            throw new WeChatException("标签id错误");
        }
        String result = WeChatRequest.post(WeChatApiUrls.DELETE_USER_TAG_URL, new DeleteWechatUserTag(id), credentialHolder);
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId      标签id
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 粉丝的openId的list
     */
    public List<String> userTagFans(int tagId, String nextOpenId) throws WeChatException {
        if (tagId < 0) {
            throw new WeChatException("标签id错误");
        }
        String result = WeChatRequest.post(WeChatApiUrls.USER_TAGS_FANS_URL, new GetWechatUserTagFans(tagId, nextOpenId), credentialHolder);
        WeChatUserTagFansResult wechatUserTagFansResult = WeChatResponse.result2Bean(result, WeChatUserTagFansResult.class);
        return wechatUserTagFansResult != null ? wechatUserTagFansResult.getData().getOpenId() : null;
    }

    /**
     * 批量为用户打标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchSetUserTag(int tagId, List<String> openIdList) throws WeChatException {
        if (tagId < 0) {
            throw new WeChatException("标签id错误");
        }
        int numLimit = 50;
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > numLimit) {
            throw new WeChatException("传入的openId有误，为空或者超过50个");
        }
        String result = WeChatRequest.post(WeChatApiUrls.BATCH_SET_USER_TAG_URL, new BatchOperateWechatUserTag(tagId, openIdList), credentialHolder);
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 批量为用户取消标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchCancelUserTag(int tagId, List<String> openIdList) throws WeChatException {
        if (tagId < 0) {
            throw new WeChatException("标签id错误");
        }
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > 50) {
            throw new WeChatException("传入的openId有误，为空或者超过50个");
        }
        String result = WeChatRequest.post(WeChatApiUrls.BATCH_CANCEL_USER_TAG_URL, new BatchOperateWechatUserTag(tagId, openIdList), credentialHolder);
        return WeChatResponse.errorInfo2Boolean(result);
    }

    /**
     * 获取用户身上的标签列表
     *
     * @param openId 用户的userId
     * @return 标签列表
     */
    public List<Integer> userTagsForUser(String openId) {
        if (StringUtil.isBlank(openId)) {
            throw new WeChatException("openId有误");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("openid", openId);
        String result = WeChatRequest.post(WeChatApiUrls.GET_USER_TAGS_FOR_USER_URL, params, credentialHolder);
        WeChatUserTagsForUserResult wechatUserTagsForUserResult = WeChatResponse.result2Bean(result, WeChatUserTagsForUserResult.class);
        return wechatUserTagsForUserResult != null ? wechatUserTagsForUserResult.getTagIdList() : null;
    }

    /*---- 设置用户备注名 ----*/

    /**
     * 设置用户备注名
     * 开发者可以通过该接口对指定用户设置备注名，该接口暂时开放给微信认证的服务号
     *
     * @param openId     用户openid
     * @param remarkName 备注名
     * @return true=成功
     */
    public boolean updateUserRemarkName(String openId, String remarkName) throws WeChatException {
        if (StringUtil.isBlank(openId)) {
            throw new WeChatException("openId有误");
        }
        if (StringUtil.isBlank(remarkName)) {
            throw new WeChatException("用户备注名有误");
        }
        String result = WeChatRequest.post(WeChatApiUrls.UPDATE_USER_REMARK_NAME_URL, new UpdateWechatUserRemarkName(openId, remarkName), credentialHolder);
        return errorInfo2Boolean(result);
    }

    /*---- 获取用户基本信息 ----*/

    /**
     * 获取用户基本信息（包括UnionID机制）
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他内容返回简体
     * @return 微信用户信息bean
     */
    public WeChatUser userInfo(String openId, String lang) throws WeChatException {
        if (StringUtil.isBlank(openId)) {
            throw new WeChatException("参数 openId 为空");
        }
        lang = WeChatUtil.standardLang(lang);
        String result = WeChatRequest.get(WeChatApiUrls.USER_INFO_URL.replace("OPENID", openId).replace("LANG", lang), credentialHolder);
        return WeChatResponse.result2Bean(result, WeChatUser.class);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param lang       返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他内容返回简体
     * @param openIdList 批量获取用户信息的用户openId的list
     * @return 批量用户列表
     */
    public List<WeChatUser> batchGetUserInfo(String lang, List<String> openIdList) throws WeChatException {
        if (openIdList == null || openIdList.size() == 0) {
            throw new WeChatException("参数 openId 为空");
        }
        lang = WeChatUtil.standardLang(lang);
        String result = WeChatRequest.post(WeChatApiUrls.BATCH_GET_USER_INFO_URL, new BatchGetWechatUserInfo(lang, openIdList), credentialHolder);
        WeChatUserInfoListResult wechatUserInfoListResult = WeChatResponse.result2Bean(result, WeChatUserInfoListResult.class);
        return wechatUserInfoListResult != null ? wechatUserInfoListResult.getUserInfoList() : null;
    }

    /* ---- 获取用户列表 ----*/

    /**
     * 获取用户列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 用户列表信息
     */
    public WeChatUserOpenIdList userList(String nextOpenId) throws WeChatException {
        if (nextOpenId == null) {
            nextOpenId = "";
        }
        String result = WeChatRequest.get(WeChatApiUrls.USER_LIST_URL.replace("NEXT_OPENID", nextOpenId), credentialHolder);
        return WeChatResponse.result2Bean(result, WeChatUserOpenIdList.class);
    }

    /*---- 黑名单管理----*/

    /**
     * 获取公众号的黑名单列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 黑名单列表结果(替换掉原有返回结果集)
     */
    public WeChatUserOpenIdList blacklist(String nextOpenId) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("begin_openid", nextOpenId);
        String result = WeChatRequest.post(WeChatApiUrls.BLACKLIST_URL, params, credentialHolder);
        WeChatUserBlacklistResult wechatUserBlacklistResult = WeChatResponse.result2Bean(result, WeChatUserBlacklistResult.class);
        WeChatUserOpenIdList wechatUserOpenIdList = new WeChatUserOpenIdList();
        wechatUserOpenIdList.setCount(wechatUserBlacklistResult.getCount());
        wechatUserOpenIdList.setTotal(wechatUserBlacklistResult.getTotal());
        wechatUserOpenIdList.setOpenIdList(wechatUserBlacklistResult.getData().getOpenIdList());
        wechatUserOpenIdList.setNextOpenid(wechatUserBlacklistResult.getNextOpenid());
        return wechatUserOpenIdList;
    }

    /**
     * 拉黑用户/取消拉黑用户
     * 一次拉黑最多允许20个
     *
     * @param openIdList 拉黑/取消拉黑的列表
     * @return true=成功
     */
    public boolean blackUserOperate(boolean operate, List<String> openIdList) {
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > 20) {
            throw new WeChatException("openId列表为空，或者超过20");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("openid_list", openIdList);
        String result = WeChatRequest.post(operate ? WeChatApiUrls.BATCH_BLACK_USER_URL : WeChatApiUrls.BATCH_UNBLACK_USER_URL,
                params, credentialHolder);
        return errorInfo2Boolean(result);
    }

    private boolean errorInfo2Boolean(String result) {
        ErrorInfo errorInfo = WeChatResponse.result2Bean(result, ErrorInfo.class);
        if (errorInfo.getErrorCode() == WeChatErrorCode.OK.getCode()) {
            return true;
        } else {
            throw new WeChatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }
}
