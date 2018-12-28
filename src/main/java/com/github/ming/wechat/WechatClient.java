package com.github.ming.wechat;

import com.github.ming.wechat.client.WechatUserClient;
import com.github.ming.wechat.client.bean.user.WechatUser;
import com.github.ming.wechat.client.bean.user.WechatUserTag;
import com.github.ming.wechat.client.config.WechatConfig;
import com.github.ming.wechat.client.exception.WechatException;

import java.util.List;

/**
 * WechatClient
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 01:39
 */
public class WechatClient {

    private final WechatUserClient userClient;

    public WechatClient(String appId, String appSecret) {
        WechatConfig.appId = appId;
        WechatConfig.appSecret = appSecret;
        userClient = WechatUserClient.getInstance();
    }

    public WechatClient(String appId, String appSecret, int timeoutRetry) {
        WechatConfig.appId = appId;
        WechatConfig.appSecret = appSecret;
        WechatConfig.accessTokenTimeoutRetry = timeoutRetry;
        userClient = WechatUserClient.getInstance();
    }

    /**
     * 创建标签
     *
     * @param tagName 创建的标签名字，30个字符以内
     * @return 创建成功的标签
     */
    public WechatUserTag createUserTag(String tagName) {
        return userClient.createUserTag(tagName);
    }

    /**
     * 获取公众号已创建的标签
     *
     * @return 标签列表
     */
    public List<WechatUserTag> userTagList() {
        return userClient.userTags();
    }

    /**
     * 编辑标签
     *
     * @param id   标签id
     * @param name 标签名
     * @return true=更新成功
     */
    public boolean updateUserTag(int id, String name) {
        return userClient.updateUserTag(id, name);
    }

    /**
     * 删除标签
     * 请注意，当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，
     * 先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
     *
     * @param id 要删除的标签id
     * @return true=更新成功
     */
    public boolean deleteUserTag(int id) {
        return userClient.deleteUserTag(id);
    }

    /**
     * 获取标签下粉丝列表
     *
     * @param tagId      标签id
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 粉丝的openId的list
     */
    public List<String> userTagFans(int tagId, String nextOpenId) throws WechatException {
        return userClient.userTagFans(tagId, nextOpenId);
    }

    /**
     * 批量为用户打标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchSetUserTag(int tagId, List<String> openIdList) throws WechatException {
        return userClient.batchSetUserTag(tagId, openIdList);
    }

    /**
     * 批量为用户取消标签
     *
     * @param tagId      标签id
     * @param openIdList 用户openId，每次传入的openid列表个数不能超过50个
     * @return true=成功
     */
    public boolean batchCancelUserTag(int tagId, List<String> openIdList) throws WechatException {
        return userClient.batchCancelUserTag(tagId, openIdList);
    }

    /**
     * 获取用户身上的标签列表
     *
     * @param openId 用户的userId
     * @return 标签列表
     */
    public List<Integer> userTagsForUser(String openId) {
        return userClient.userTagsForUser(openId);
    }

    /**
     * 设置用户备注名
     * 开发者可以通过该接口对指定用户设置备注名，该接口暂时开放给微信认证的服务号
     *
     * @param openId     用户openid
     * @param remarkName 备注名
     * @return true=成功
     * @throws WechatException
     */
    public boolean updateUserRemarkName(String openId, String remarkName) throws WechatException {
        return userClient.updateUserRemarkName(openId, remarkName);
    }

    /**
     * 根据openid获取用户信息
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @param lang   返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他返回简体
     * @return 微信用户信息bean
     */
    public WechatUser userInfo(String openId, String lang) {
        return userClient.userInfo(openId, lang);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param lang       返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，传其他内容返回简体
     * @param openIdList 批量获取用户信息的用户openId的list
     * @return
     * @throws WechatException
     */
    public List<WechatUser> batchGetUserInfo(String lang, List<String> openIdList) throws WechatException {
        return userClient.batchGetUserInfo(lang, openIdList);
    }

}
