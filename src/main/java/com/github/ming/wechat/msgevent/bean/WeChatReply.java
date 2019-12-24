package com.github.ming.wechat.msgevent.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * WeChatReply
 *
 * @author ZM
 * @date : 2019-01-01 00:11
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeChatReply {

    /**
     * 接收方帐号（收到的OpenID）
     */
    @XmlCDATA
    @XmlElement(name = "ToUserName")
    private String toUserName;

    /**
     * 开发者微信号
     */
    @XmlCDATA
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XmlElement(name = "CreateTime")
    private Integer createTime;

    /**
     * 消息类型
     */
    @XmlCDATA
    @XmlElement(name = "MsgType")
    private String msgType;

    /**
     * 通过素材管理中的接口上传多媒体文件，得到的id
     */
    @XmlElement(name = "MediaId")
    private String mediaId;

    /**
     * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */
    @XmlCDATA
    @XmlElement(name = "Content")
    private String content;

    /**
     * 图片消息
     */
    @XmlElement(name = "Image")
    private WeChatReplyImage image;

    /**
     * 语音消息
     */
    @XmlElement(name = "Voice")
    private WeChatReplyVoice voice;

    /**
     * 视频消息
     */
    @XmlElement(name = "Video")
    private WeChatReplyVideo video;

    /**
     * 音乐消息
     */
    @XmlElement(name = "Music")
    private WeChatReplyMusic music;

    /**
     * 图文消息个数；当用户发送文本、图片、视频、图文、地理位置这五种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     */
    @XmlElement(name = "ArticleCount")
    private Integer articleCount;

    /**
     * 图文消息
     */
    @XmlElement(name = "item")
    @XmlElementWrapper(name = "Articles")
    private List<WeChatReplyArticle> articles;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public WeChatReplyImage getImage() {
        return image;
    }

    public void setImage(WeChatReplyImage image) {
        this.image = image;
    }

    public WeChatReplyVoice getVoice() {
        return voice;
    }

    public void setVoice(WeChatReplyVoice voice) {
        this.voice = voice;
    }

    public WeChatReplyVideo getVideo() {
        return video;
    }

    public void setVideo(WeChatReplyVideo video) {
        this.video = video;
    }

    public WeChatReplyMusic getMusic() {
        return music;
    }

    public void setMusic(WeChatReplyMusic music) {
        this.music = music;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public List<WeChatReplyArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<WeChatReplyArticle> articles) {
        this.articles = articles;
    }
}
