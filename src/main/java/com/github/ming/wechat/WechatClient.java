package com.github.ming.wechat;

import com.github.ming.wechat.client.*;
import com.github.ming.wechat.client.bean.media.WechatMaterialVideoDesc;
import com.github.ming.wechat.client.bean.media.WechatMediaImageText;
import com.github.ming.wechat.client.bean.media.WechatMediaInfo;
import com.github.ming.wechat.client.bean.menu.WechatMenuButton;
import com.github.ming.wechat.client.bean.menu.WechatMenuButtonGroup;
import com.github.ming.wechat.client.bean.menu.response.WechatMenusResult;
import com.github.ming.wechat.client.bean.qrcode.request.WechatQrCode;
import com.github.ming.wechat.client.bean.qrcode.response.WechatQrCodeResult;
import com.github.ming.wechat.client.bean.user.WechatUser;
import com.github.ming.wechat.client.bean.user.WechatUserOpenIdList;
import com.github.ming.wechat.client.bean.user.WechatUserTag;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.msgevent.bean.WechatEvent;
import com.github.ming.wechat.msgevent.bean.WechatReply;
import com.github.ming.wechat.msgevent.handler.WechatMsgEventHandler;

import java.io.File;
import java.util.List;

/**
 * WechatClient
 *
 * @author : Liu Zeming
 * @date : 2018-12-14 01:39
 */
public class WechatClient {

    private final WechatMenuClient menuClient;

    private final WechatUserClient userClient;

    private final WechatMediaClient mediaClient;

    private final WechatAcountManageClient acountManageClient;

    private WechatMsgEventHandler msgEventHandler;

    public WechatClient(String appId, String appSecret, int timeoutRetry) {
        WechatCredentialHolder credentialHolder = new WechatCredentialHolder(appId, appSecret, timeoutRetry);
        menuClient = new WechatMenuClient(credentialHolder);
        userClient = new WechatUserClient(credentialHolder);
        mediaClient = new WechatMediaClient(credentialHolder);
        acountManageClient = new WechatAcountManageClient(credentialHolder);
    }

    public WechatClient(String appId, String appSecret, int timeoutRetry, boolean openEncryption, String eventToken, String encodingAESKey) {
        WechatCredentialHolder credentialHolder = new WechatCredentialHolder(appId, appSecret, timeoutRetry);
        msgEventHandler = new WechatMsgEventHandler(openEncryption, appId, eventToken, encodingAESKey);
        menuClient = new WechatMenuClient(credentialHolder);
        userClient = new WechatUserClient(credentialHolder);
        mediaClient = new WechatMediaClient(credentialHolder);
        acountManageClient = new WechatAcountManageClient(credentialHolder);
    }

    /*---- 菜单管理 ----*/

    /**
     * 自定义菜单创建
     * 自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单
     *
     * @param menuButtonList 创建菜单参数
     * @return true=成功
     */
    public boolean createMenu(List<WechatMenuButton> menuButtonList) throws WechatException {
        return menuClient.createMenu(menuButtonList);
    }

    /**
     * 自定义菜单查询
     *
     * @return 菜单列表
     */
    public WechatMenusResult menus() throws WechatException {
        return menuClient.menus();
    }

    /**
     * 自定义菜单删除
     *
     * @return true=成功
     */
    public boolean deleteMenus() throws WechatException {
        return menuClient.deleteMenus();
    }

    /**
     * 创建个性化菜单
     * 自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单
     *
     * @param wechatMenuButtonGroup 个性化菜单参数
     * @return true=成功
     */
    public boolean createConditionalMenu(WechatMenuButtonGroup wechatMenuButtonGroup) throws WechatException {
        return menuClient.createConditionalMenu(wechatMenuButtonGroup);
    }

    /**
     * 删除个性化菜单
     *
     * @return true=成功
     */
    public boolean deleteConditionalMenus(int menuId) throws WechatException {
        return menuClient.deleteConditionalMenus(menuId);
    }

    /**
     * 测试个性化菜单匹配结果
     *
     * @param userId user_id可以是粉丝的OpenID，也可以是粉丝的微信号
     * @return 请求结果
     */
    public WechatMenuButtonGroup tryMatchMenu(String userId) throws WechatException {
        return menuClient.tryMatchMenu(userId);
    }

    /*---- 用户管理 ----*/

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
     * @return 用户信息列表
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
        return userClient.blackUserOperate(true, openIdList);
    }

    /**
     * 取消拉黑用户
     * 一次拉黑最多允许20个
     *
     * @param openIdList 取消拉黑的列表
     * @return true=成功
     */
    public boolean unblackUser(List<String> openIdList) {
        return userClient.blackUserOperate(false, openIdList);
    }

    /*---- 素材管理 ----*/

    /**
     * 新增临时素材
     * 媒体文件在微信后台保存时间为3天，即3天后media_id失效
     *
     * @param file 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式；
     *             语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式；
     *             视频（video）：10MB，支持MP4格式；缩略图（thumb）：64KB，支持JPG格式；
     *             缩略图（thumb）：64KB，支持JPG格式；
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return 上传结果
     */
    public WechatMediaInfo uploadMedia(File file, String type) throws WechatException {
        return mediaClient.uploadMedia(file, type);
    }

    /**
     * 新增永久图文素材
     *
     * @param imageTextList 图文list
     * @return 素材id
     */
    public String uploadPermanentMedia(List<WechatMediaImageText> imageTextList) throws WechatException {
        return mediaClient.uploadPermanentMedia(imageTextList);
    }

    /**
     * 上传图文消息内的图片获取URL
     * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下
     *
     * @param file 图片file
     * @return 图片url
     */
    public String uploadMediaImg(File file) throws WechatException {
        return mediaClient.uploadMediaImg(file);
    }

    /**
     * 新增永久素材，不可以上传视频类型
     *
     * @param file 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式；
     *             语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式；
     *             缩略图（thumb）：64KB，支持JPG格式；
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return 上传结果
     */
    public WechatMediaInfo uploadMaterial(File file, String type) throws WechatException {
        return mediaClient.uploadMaterial(file, type);
    }

    /**
     * 新增永久视频类型素材，不可以上传其他类型
     *
     * @param file 视频（video）：10MB，支持MP4格式；缩略图（thumb）：64KB，支持JPG格式；
     * @param desc type=video时，desc必传；type为其他类型时，传空即可
     * @return 上传结果
     */
    public WechatMediaInfo uploadMaterialVideo(File file, WechatMaterialVideoDesc desc) throws WechatException {
        return mediaClient.uploadMaterialVideo(file, desc);
    }

    /*---- 账号管理 ----*/

    /**
     * 生成带参数的二维码
     *
     * @param wechatQrCode 参数
     * @return 请求结果
     */
    public WechatQrCodeResult createQrCode(WechatQrCode wechatQrCode) {
        return acountManageClient.createQrCode(wechatQrCode);
    }

    /*---- 接收消息、事件 ----*/

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
        return msgEventHandler.xml2WechatEvent(timestamp, nonce, encryptType, msgSignature, requestBody);
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
