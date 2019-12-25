package com.github.ming.wechat.msgevent.handler;

import com.github.ming.wechat.client.exception.WeChatException;
import com.github.ming.wechat.msgevent.aes.AesException;
import com.github.ming.wechat.msgevent.aes.WXBizMsgCrypt;
import com.github.ming.wechat.msgevent.bean.WeChatEvent;
import com.github.ming.wechat.msgevent.bean.WeChatReply;
import com.github.ming.wechat.util.StringUtil;
import com.github.ming.wechat.util.XmlUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.TreeSet;

/**
 * 微信发来的普通消息、事件消息、被动回复消息的处理
 *
 * @author ZM
 * @date : 2018-12-29 06:28
 */
public class WeChatMsgEventHandler {

    /**
     * 服务器配置中的token
     */
    private String eventToken;

    /**
     * 消息加密类型
     */
    private static final String ENCRYPT_TYPE = "aes";

    private WXBizMsgCrypt wxBizMsgCrypt;

    /**
     * WechatEventHandler
     *
     * @param openEncryption 是否开启消息加密（true=开启；false=未开启）
     * @param appId          第三方用户唯一凭证
     * @param eventToken     服务器配置中的token
     * @param encodingAesKey 消息加解密密钥
     */
    public WeChatMsgEventHandler(boolean openEncryption, String appId, String eventToken, String encodingAesKey) {
        this.eventToken = eventToken;
        if (openEncryption) {
            try {
                wxBizMsgCrypt = new WXBizMsgCrypt(eventToken, encodingAesKey, appId);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 微信服务器验证
     *
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 验证成功返回随即字符串，否则返回null
     */
    public boolean serverValidate(String signature, String timestamp, String nonce) {
        TreeSet<String> paramSet = new TreeSet<>();
        paramSet.add(nonce);
        paramSet.add(timestamp);
        paramSet.add(this.eventToken);
        String validateValue = DigestUtils.sha1Hex(paramSet.pollFirst() + paramSet.pollFirst() + paramSet.pollFirst());
        return validateValue.equals(signature);
    }

    /**
     * 处理接收到的微信发来的消息、事件推送，由xml转为WechatEvent
     * 加密消息、非加密消息皆可以用此方法处理，是否开启加密在WechatMsgEventHandler初始化的时候配置。
     * 方法参数全部可从微信发来的消息推送的post请求中获取到，可直接在接收推送的post接口中设置相应参数获取，然后传入本方法即可。
     *
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param encryptType  加密类型（目前微信采用aes加密）
     * @param msgSignature 消息体签名
     * @param requestBody  微信发来的请求体
     * @return WechatEvent
     */
    public WeChatEvent xml2WechatEvent(String timestamp, String nonce, String encryptType, String msgSignature,
                                       String requestBody) throws WeChatException {
        if (ENCRYPT_TYPE.equals(encryptType)) {
            if (wxBizMsgCrypt == null) {
                throw new WeChatException("未设置服务器配置参数 Token 与 EncodingAESKey");
            }
            try {
                String decryptEventMsg = wxBizMsgCrypt.decryptMsg(msgSignature, timestamp, nonce, requestBody);
                return XmlUtil.xmlToBean(decryptEventMsg, WeChatEvent.class);
            } catch (AesException e) {
                e.printStackTrace();
                throw new WeChatException("aes解密错误：" + e.getMessage());
            }
        } else {
            return XmlUtil.xmlToBean(requestBody, WeChatEvent.class);
        }
    }

    /**
     * 微信发来的消息、事件推送的回复，由WechatReply转为xml
     * 回复消息的加密消息、非加密消息皆可以用此方法处理，是否开启加密在WechatMsgEventHandler初始化的时候配置。
     *
     * @param reply 回复的内容bean
     * @return xml字符串，开启加密的话即为加密后的xml字符串。
     */
    public String wechatReply2Xml(WeChatReply reply) {
        String replyXml = XmlUtil.beanToXml(reply);
        if (wxBizMsgCrypt == null) {
            return replyXml;
        } else {
            try {
                return wxBizMsgCrypt.encryptMsg(replyXml, String.valueOf(System.currentTimeMillis() / 1000),
                        StringUtil.randomStr(18));
            } catch (AesException e) {
                e.printStackTrace();
            }
            return replyXml;
        }
    }

}
