package com.github.ming.wechat;

import com.github.ming.wechat.client.WechatCredentialHolder;
import com.github.ming.wechat.client.WechatUserClient;
import com.github.ming.wechat.client.bean.user.WechatUser;
import com.github.ming.wechat.client.bean.user.WechatUserOpenIdList;
import com.github.ming.wechat.client.bean.user.WechatUserTag;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.event.bean.WechatEvent;
import com.github.ming.wechat.event.bean.WechatReply;
import com.github.ming.wechat.event.handler.WechatMsgEventHandler;

import java.util.List;

/**
 * WechatClient
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 01:39
 */
public class WechatClient {

    private final WechatUserClient userClient;

    private WechatMsgEventHandler msgEventHandler;

    public WechatClient(String appId, String appSecret, int timeoutRetry) {
        WechatCredentialHolder credentialHolder = new WechatCredentialHolder(appId, appSecret, timeoutRetry);
        userClient = new WechatUserClient(credentialHolder);
    }

    public WechatClient(String appId, String appSecret, int timeoutRetry, boolean openEncryption, String eventToken, String encodingAESKey) {
        WechatCredentialHolder credentialHolder = new WechatCredentialHolder(appId, appSecret, timeoutRetry);
        msgEventHandler = new WechatMsgEventHandler(openEncryption, appId, eventToken, encodingAESKey);
        userClient = new WechatUserClient(credentialHolder);
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

    /**
     * 获取用户列表
     *
     * @param nextOpenId 第一个拉取的OPENID，传空或""默认从头拉取
     * @return 用户列表信息
     */
    public WechatUserOpenIdList userList(String nextOpenId) throws WechatException {
        return userClient.userList(nextOpenId);
    }

    /**
     * 获取公众号的黑名单列表
     *
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
     * @return 黑名单列表结果
     */
    public WechatUserOpenIdList blacklist(String nextOpenId) {
        return userClient.blacklist(nextOpenId);
    }

    /**
     * 拉黑用户
     * 一次拉黑最多允许20个
     *
     * @param openIdList 拉黑的列表
     * @return true=成功
     */
    public boolean blackUser(List<String> openIdList) {
        return userClient.blackUser(openIdList);
    }

    /**
     * 微信服务器验证
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp timestamp
     * @param nonce     nonce
     * @return true=验证成功
     */
    public boolean serverValidate(String signature, String timestamp, String nonce) {
        if (msgEventHandler == null) {
            throw new WechatException("未初始化WechatEventHandler");
        }
        return msgEventHandler.serverValidate(signature, timestamp, nonce);
    }

    /**
     * 处理接收到的微信发来的消息、事件推送，由xml转为WechatEvent
     * 加密消息、非加密消息皆可以用此方法处理，是否开启加密在WechatMsgEventHandler初始化的时候配置。
     * 方法参数全部可从微信发来的消息推送的post请求中获取到，可直接在接收推送的post接口中设置相应参数获取，然后传入本方法即可。
     *
     * @param timestamp    timestamp
     * @param nonce        nonce
     * @param encryptType  encrypt_type
     * @param msgSignature msg_signature
     * @param requestBody  requestBody
     * @return WechatEvent
     */
    public WechatEvent xml2WechatEvent(String timestamp, String nonce, String encryptType, String msgSignature,
                                     String requestBody) throws WechatException {
        if (msgEventHandler == null) {
            throw new WechatException("未初始化WechatEventHandler");
        }
        return msgEventHandler.handleReceive(timestamp, nonce, encryptType, msgSignature, requestBody);
    }

    /**
     * 微信发来的消息、事件推送的回复，由WechatReply转为xml
     * 回复消息的加密消息、非加密消息皆可以用此方法处理，是否开启加密在WechatMsgEventHandler初始化的时候配置。
     *
     * @param reply 回复的内容bean
     * @return xml字符串，开启加密的话即为加密后的xml字符串。
     */
    public String wechatReply2Xml(WechatReply reply) {
        return msgEventHandler.wechatReply2Xml(reply);
    }

}
