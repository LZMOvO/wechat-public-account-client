package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.bean.user.WechatUser;
import com.github.ming.wechat.client.bean.user.WechatUserOpenIdList;
import com.github.ming.wechat.client.bean.user.WechatUserTag;
import com.github.ming.wechat.client.bean.user.request.*;
import com.github.ming.wechat.client.bean.user.response.*;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.util.StringUtil;
import com.github.ming.wechat.util.WechatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 17:36
 */
public final class WechatUserClient {

    private WechatCredentialHolder credentialHolder;

    public WechatUserClient(WechatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /*---- 用户标签管理 ----*/

    /**
     * 创建标签
     *
     * @param name 创建的标签名字，30个字符以内
     * @return 返回创建标签信息
     */
    public WechatUserTag createUserTag(String name) throws WechatException {
        if (StringUtil.isBlank(name)) {
            throw new WechatException("参数 openId 为空");
        }
        String result = WechatRequest.post(WechatApiUrls.CREATE_USER_TAG_URL, new CreateWechatUserTag(name), credentialHolder);
        WechatUserTagResult wechatUserTagResult = WechatResponse.result2Bean(result, WechatUserTagResult.class);
        return wechatUserTagResult != null ? wechatUserTagResult.getTag() : null;
    }

    /**
     * 获取公众号已创建的标签
     *
     * @return 标签列表
     */
    public List<WechatUserTag> userTags() throws WechatException {
        String result = WechatRequest.get(WechatApiUrls.GET_USER_TAGS_URL, credentialHolder);
        WechatUserTagListResult wechatUserTagList = WechatResponse.result2Bean(result, WechatUserTagListResult.class);
        return wechatUserTagList != null ? wechatUserTagList.getTags() : null;
    }

    /**
     * 编辑标签
     *
     * @param id   标签id
     * @param name 标签名
     * @return true=更新成功
     */
    public boolean updateUserTag(int id, String name) throws WechatException {
        if (id < 0) {
            throw new WechatException("标签id错误");
        }
        if (StringUtil.isBlank(name)) {
            throw new WechatException("标签名为空");
        }
        String result = WechatRequest.post(WechatApiUrls.UPDATE_USER_TAG_URL, new UpdateWechatUserTag(id, name), credentialHolder);
        return errorInfo2Boolean(result);
    }

    /**
     * 删除标签
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，
     * 先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param id 要删除的标签id
     * @return true=更新成功
     */
    public boolean deleteUserTag(int id) throws WechatException {
        if (id < 0) {
            throw new WechatException("标签id错误");
        }
        String result = WechatRequest.post(WechatApiUrls.DELETE_USER_TAG_URL, new DeleteWechatUserTag(id), credentialHolder);
        return errorInfo2Boolean(result);
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId      标签id
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 粉丝的openId的list
     */
    public List<String> userTagFans(int tagId, String nextOpenId) throws WechatException {
        if (tagId < 0) {
            throw new WechatException("标签id错误");
        }
        String result = WechatRequest.post(WechatApiUrls.USER_TAGS_FANS_URL, new GetWechatUserTagFans(tagId, nextOpenId), credentialHolder);
        WechatUserTagFansResult wechatUserTagFansResult = WechatResponse.result2Bean(result, WechatUserTagFansResult.class);
        return wechatUserTagFansResult != null ? wechatUserTagFansResult.getData().getOpenId() : null;
    }

    /**
     * 批量为用户打标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchSetUserTag(int tagId, List<String> openIdList) throws WechatException {
        if (tagId < 0) {
            throw new WechatException("标签id错误");
        }
        int numLimit = 50;
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > numLimit) {
            throw new WechatException("传入的openId有误，为空或者超过50个");
        }
        String result = WechatRequest.post(WechatApiUrls.BATCH_SET_USER_TAG_URL, new BatchOperateWechatUserTag(tagId, openIdList), credentialHolder);
        return errorInfo2Boolean(result);
    }

    /**
     * 批量为用户取消标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchCancelUserTag(int tagId, List<String> openIdList) throws WechatException {
        if (tagId < 0) {
            throw new WechatException("标签id错误");
        }
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > 50) {
            throw new WechatException("传入的openId有误，为空或者超过50个");
        }
        String result = WechatRequest.post(WechatApiUrls.BATCH_CANCEL_USER_TAG_URL, new BatchOperateWechatUserTag(tagId, openIdList), credentialHolder);
        return errorInfo2Boolean(result);
    }

    /**
     * 获取用户身上的标签列表
     *
     * @param openId 用户的userId
     * @return 标签列表
     */
    public List<Integer> userTagsForUser(String openId) {
        if (StringUtil.isBlank(openId)) {
            throw new WechatException("openId有误");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("openid", openId);
        String result = WechatRequest.post(WechatApiUrls.GET_USER_TAGS_FOR_USER_URL, params, credentialHolder);
        WechatUserTagsForUserResult wechatUserTagsForUserResult = WechatResponse.result2Bean(result, WechatUserTagsForUserResult.class);
        params = null;
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
    public boolean updateUserRemarkName(String openId, String remarkName) throws WechatException {
        if (StringUtil.isBlank(openId)) {
            throw new WechatException("openId有误");
        }
        if (StringUtil.isBlank(remarkName)) {
            throw new WechatException("用户备注名有误");
        }
        String result = WechatRequest.post(WechatApiUrls.UPDATE_USER_REMARK_NAME_URL, new UpdateWechatUserRemarkName(openId, remarkName), credentialHolder);
        ErrorInfo errorInfo = WechatResponse.result2Bean(result, ErrorInfo.class);
        if (errorInfo.getErrorCode() == 0) {
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /*---- 获取用户基本信息 ----*/

    /**
     * 获取用户基本信息（包括UnionID机制）
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他内容返回简体
     * @return 微信用户信息bean
     */
    public WechatUser userInfo(String openId, String lang) throws WechatException {
        if (StringUtil.isBlank(openId)) {
            throw new WechatException("参数 openId 为空");
        }
        lang = WechatUtil.standardLang(lang);
        String result = WechatRequest.get(WechatApiUrls.USER_INFO_URL.replace("OPENID", openId).replace("LANG", lang), credentialHolder);
        return WechatResponse.result2Bean(result, WechatUser.class);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param lang       返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他内容返回简体
     * @param openIdList 批量获取用户信息的用户openId的list
     * @return 批量用户列表
     */
    public List<WechatUser> batchGetUserInfo(String lang, List<String> openIdList) throws WechatException {
        if (openIdList == null || openIdList.size() == 0) {
            throw new WechatException("参数 openId 为空");
        }
        lang = WechatUtil.standardLang(lang);
        String result = WechatRequest.post(WechatApiUrls.BATCH_GET_USER_INFO_URL, new BatchGetWechatUserInfo(lang, openIdList), credentialHolder);
        WechatUserInfoListResult wechatUserInfoListResult = WechatResponse.result2Bean(result, WechatUserInfoListResult.class);
        return wechatUserInfoListResult != null ? wechatUserInfoListResult.getUserInfoList() : null;
    }

    /* ---- 获取用户列表 ----*/

    /**
     * 获取用户列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 用户列表信息
     */
    public WechatUserOpenIdList userList(String nextOpenId) throws WechatException {
        if (nextOpenId == null) {
            nextOpenId = "";
        }
        String result = WechatRequest.get(WechatApiUrls.USER_LIST_URL.replace("NEXT_OPENID", nextOpenId), credentialHolder);
        return WechatResponse.result2Bean(result, WechatUserOpenIdList.class);
    }

    /*---- 黑名单管理----*/

    /**
     * 获取公众号的黑名单列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 黑名单列表结果(替换掉原有返回结果集)
     */
    public WechatUserOpenIdList blacklist(String nextOpenId) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("begin_openid", nextOpenId);
        String result = WechatRequest.post(WechatApiUrls.BLACKLIST_URL, params, credentialHolder);
        WechatUserBlacklistResult wechatUserBlacklistResult = WechatResponse.result2Bean(result, WechatUserBlacklistResult.class);
        WechatUserOpenIdList wechatUserOpenIdList = new WechatUserOpenIdList();
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
            throw new WechatException("openId列表为空，或者超过20");
        }
        Map<String, Object> params = new HashMap<>(3);
        params.put("openid_list", openIdList);
        String result = WechatRequest.post(operate ? WechatApiUrls.BATCH_BLACK_USER_URL : WechatApiUrls.BATCH_UNBLACK_USER_URL,
                params, credentialHolder);
        ErrorInfo errorInfo = WechatResponse.result2Bean(result, ErrorInfo.class);
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            params = null;
            return true;
        } else {
            params = null;
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    private boolean errorInfo2Boolean(String result) {
        return errorInfo2Boolean(result);
    }

}
