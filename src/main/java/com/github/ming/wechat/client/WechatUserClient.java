package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.result.ErrorInfo;
import com.github.ming.wechat.client.bean.user.WechatUser;
import com.github.ming.wechat.client.bean.user.WechatUserOpenIdList;
import com.github.ming.wechat.client.bean.user.WechatUserTag;
import com.github.ming.wechat.client.bean.user.request.*;
import com.github.ming.wechat.client.bean.user.response.*;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.client.handler.WechatResultHandler;
import com.github.ming.wechat.util.HttpUtil;
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
        WechatUserTagResult wechatUserTagResult = createUserTagPost(new CreateWechatUserTag(name));
        return wechatUserTagResult != null ? wechatUserTagResult.getTag() : null;
    }

    /**
     * 创建标签 实际接口调用
     * 一个公众号，最多可以创建100个标签
     * accessToken超时重试
     *
     * @param postUserTag 提交的参数体
     */
    private WechatUserTagResult createUserTagPost(CreateWechatUserTag postUserTag) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.CREATE_USER_TAG_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(postUserTag));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                ++times;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        postUserTag = null;
        return WechatResultHandler.result2Bean(result, WechatUserTagResult.class);
    }

    /**
     * 获取公众号已创建的标签
     *
     * @return 标签列表
     */
    public List<WechatUserTag> userTags() throws WechatException {
        WechatUserTagListResult wechatUserTagList = userTagsGet();
        return wechatUserTagList != null ? wechatUserTagList.getTags() : null;
    }

    /**
     * 获取公众号已创建的标签 实际接口调用
     * accessToken超时重试
     *
     * @return 按照微信返回格式创建的list
     */
    private WechatUserTagListResult userTagsGet() {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(WechatApiUrls.GET_USER_TAGS_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatUserTagListResult.class);
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
        ErrorInfo errorInfo = updateUserTagPost(new UpdateWechatUserTag(id, name));
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 编辑标签 接口实际调用
     * accessToken超时重试
     *
     * @param updateWechatUserTag 编辑的标签信息
     * @return 更新的结果
     */
    private ErrorInfo updateUserTagPost(UpdateWechatUserTag updateWechatUserTag) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.UPDATE_USER_TAG_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(updateWechatUserTag));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        updateWechatUserTag = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
    }

    /**
     * 删除标签
     *
     * @param id 要删除的标签id
     * @return true=更新成功
     */
    public boolean deleteUserTag(int id) throws WechatException {
        if (id < 0) {
            throw new WechatException("标签id错误");
        }
        ErrorInfo errorInfo = deleteUserTagPost(new DeleteWechatUserTag(id));
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 删除标签 接口实际调用
     * accessToken超时重试
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，
     * 先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param deleteUserTag 删除的标签信息
     * @return 操作的结果信息
     */
    private ErrorInfo deleteUserTagPost(DeleteWechatUserTag deleteUserTag) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.DELETE_USER_TAG_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)),
                    JSON.toJSONString(deleteUserTag));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        deleteUserTag = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        WechatUserTagFansResult wechatUserTagFansResult = userTagFansPost(new GetWechatUserTagFans(tagId, nextOpenId));
        return wechatUserTagFansResult != null ? wechatUserTagFansResult.getData().getOpenId() : null;
    }

    /**
     * 获取标签下粉丝列表 接口实际调用
     * accessToken超时重试
     *
     * @param getWechatUserTagFans 参数信息
     * @return 请求结果
     */
    private WechatUserTagFansResult userTagFansPost(GetWechatUserTagFans getWechatUserTagFans) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.USER_TAGS_FANS_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)),
                    JSON.toJSONString(getWechatUserTagFans));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        getWechatUserTagFans = null;
        return WechatResultHandler.result2Bean(result, WechatUserTagFansResult.class);
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
        ErrorInfo errorInfo = batchSetUserTagPost(new BatchOperateWechatUserTag(tagId, openIdList));
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 批量为用户打标签 接口实际调用
     * accessToken超时重试
     *
     * @param batchOperateUserTag 批量操作传参
     * @return 请求结果
     */
    private ErrorInfo batchSetUserTagPost(BatchOperateWechatUserTag batchOperateUserTag) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.BATCH_SET_USER_TAG_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(batchOperateUserTag));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        batchOperateUserTag = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        ErrorInfo errorInfo = batchCancelUserTagPost(new BatchOperateWechatUserTag(tagId, openIdList));
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 批量为用户取消标签 接口实际调用
     * accessToken超时重试
     *
     * @param batchOperateUserTag 批量操作传参
     * @return 请求结果
     */
    private ErrorInfo batchCancelUserTagPost(BatchOperateWechatUserTag batchOperateUserTag) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.BATCH_CANCEL_USER_TAG_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(batchOperateUserTag));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        batchOperateUserTag = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        WechatUserTagsForUserResult result = userTagsForUserPost(openId);
        return result != null ? result.getTagIdList() : null;
    }

    /**
     * 获取用户身上的标签列表 接口实际调用
     * accessToken超时重试
     *
     * @param openId 用户的userId
     * @return 微信返回标签列表
     */
    private WechatUserTagsForUserResult userTagsForUserPost(String openId) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        Map<String, Object> params = new HashMap<>(3);
        params.put("openid", openId);
        do {
            result = HttpUtil.post(WechatApiUrls.GET_USER_TAGS_FOR_USER_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(new JSONObject(params)));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatUserTagsForUserResult.class);
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
        ErrorInfo errorInfo = updateUserRemarkNamePost(new UpdateWechatUserRemarkName(openId, remarkName));
        if (errorInfo.getErrorCode() == 0) {
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 设置用户备注名 接口实际调用
     * accessToken超时重试
     *
     * @param updateWechatUserRemarkName 设置用户备注名所需参数
     * @return 返回结果
     */
    private ErrorInfo updateUserRemarkNamePost(UpdateWechatUserRemarkName updateWechatUserRemarkName) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.UPDATE_USER_REMARK_NAME_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(updateWechatUserRemarkName));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        updateWechatUserRemarkName = null;
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
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
        return userInfoGet(openId, lang);
    }

    /**
     * 获取用户基本信息（包括UnionID机制） 实际接口调用
     * accessToken超时重试
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     */
    private WechatUser userInfoGet(String openId, String lang) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(WechatApiUrls.USER_INFO_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)).replace("OPENID", openId).replace("LANG", lang));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatUser.class);
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
        WechatUserInfoListResult result = batchGetUserInfoPost(new BatchGetWechatUserInfo(lang, openIdList));
        return result != null ? result.getUserInfoList() : null;
    }

    /**
     * 批量获取用户基本信息 实际接口调用
     * accessToken超时重试
     *
     * @param batchGetWechatUserInfo 批量获取用户基本信息参数
     * @return 微信结果集
     */
    private WechatUserInfoListResult batchGetUserInfoPost(BatchGetWechatUserInfo batchGetWechatUserInfo) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.post(WechatApiUrls.BATCH_GET_USER_INFO_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), JSON.toJSONString(batchGetWechatUserInfo));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        batchGetWechatUserInfo = null;
        return WechatResultHandler.result2Bean(result, WechatUserInfoListResult.class);
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
        return userListGet(nextOpenId);
    }

    /**
     * 获取用户列表 实际接口调用
     * accessToken超时重试
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 用户列表信息
     */
    private WechatUserOpenIdList userListGet(String nextOpenId) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            result = HttpUtil.get(WechatApiUrls.USER_LIST_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)).replace("NEXT_OPENID", nextOpenId));
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatUserOpenIdList.class);
    }
    /*---- 黑名单管理----*/

    /**
     * 获取公众号的黑名单列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 黑名单列表结果(替换掉原有返回结果集)
     */
    public WechatUserOpenIdList blacklist(String nextOpenId) {
        WechatUserBlacklistResult result = blacklistPost(nextOpenId);
        WechatUserOpenIdList wechatUserOpenIdList = new WechatUserOpenIdList();
        wechatUserOpenIdList.setCount(result.getCount());
        wechatUserOpenIdList.setTotal(result.getTotal());
        wechatUserOpenIdList.setOpenIdList(result.getData().getOpenIdList());
        wechatUserOpenIdList.setNextOpenid(result.getNextOpenid());
        return wechatUserOpenIdList;
    }

    /**
     * 获取公众号的黑名单列表 实际接口调用
     * accessToken超时重试
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 黑名单列表结果
     */
    private WechatUserBlacklistResult blacklistPost(String nextOpenId) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("begin_openid", nextOpenId);
            result = HttpUtil.post(WechatApiUrls.BLACKLIST_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), jsonObject.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, WechatUserBlacklistResult.class);
    }

    /**
     * 拉黑用户
     * 一次拉黑最多允许20个
     *
     * @param openIdList 拉黑的列表
     * @return true=成功
     */
    public boolean blackUser(List<String> openIdList) {
        return blackResultOperate(openIdList, blackUserPost(openIdList));
    }

    /**
     * 拉黑用户 实际接口调用
     * accessToken超时重试
     *
     * @param openIdList 拉黑的列表
     * @return 请求结果
     */
    private ErrorInfo blackUserPost(List<String> openIdList) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openid_list", openIdList);
            result = HttpUtil.post(WechatApiUrls.BATCH_BLACK_USER_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), jsonObject.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
    }

    /**
     * 取消拉黑用户
     * 一次拉黑最多允许20个
     *
     * @param openIdList 取消拉黑的openId列表
     * @return true=成功
     */
    public boolean unBlackUser(List<String> openIdList) {
        return blackResultOperate(openIdList, unBlackUserPost(openIdList));
    }

    private boolean blackResultOperate(List<String> openIdList, ErrorInfo errorInfo) {
        if (openIdList == null || openIdList.size() == 0 || openIdList.size() > 20) {
            throw new WechatException("openId列表为空，或者超过20");
        }
        if (errorInfo.getErrorCode() == 0) {
            errorInfo = null;
            return true;
        } else {
            throw new WechatException(errorInfo.getErrorCode(), errorInfo.getErrMsg());
        }
    }

    /**
     * 取消拉黑用户 实际接口调用
     * accessToken超时重试
     *
     * @param openIdList 取消拉黑的openId列表
     * @return 请求结果
     */
    private ErrorInfo unBlackUserPost(List<String> openIdList) {
        String result;
        boolean refreshToken = false;
        int times = 0;
        do {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openid_list", openIdList);
            result = HttpUtil.post(WechatApiUrls.BATCH_UNBLACK_USER_URL.replace("ACCESS_TOKEN",
                    credentialHolder.getAccessToken(refreshToken)), jsonObject.toJSONString());
            if (!WechatResultHandler.judgeAccessTokenTimeout(result)) {
                break;
            } else {
                refreshToken = true;
                times++;
            }
        } while (times < credentialHolder.getAccessTokenTimeoutRetry());
        return WechatResultHandler.result2Bean(result, ErrorInfo.class);
    }

}
