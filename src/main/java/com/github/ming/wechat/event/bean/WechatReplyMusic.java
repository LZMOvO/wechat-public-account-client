package com.github.ming.wechat.event.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 回复音乐消息
 *
 * @author : Liu Zeming
 * @date : 2019-01-01 01:08
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WechatReplyMusic {

    /**
     * 通过素材管理中的接口上传多媒体文件，得到的id
     */
    @XmlCDATA
    @XmlElement(name = "MediaId")
    private String mediaId;

    /**
     * 视频消息的标题
     */
    @XmlCDATA
    @XmlElement(name = "Title")
    private String title;

    /**
     * 视频消息的描述
     */
    @XmlCDATA
    @XmlElement(name = "Description")
    private String description;

    /**
     * 音乐链接
     */
    @XmlCDATA
    @XmlElement(name = "MusicURL")
    private String musicURL;

    /**
     * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     */
    @XmlCDATA
    @XmlElement(name = "HQMusicUrl")
    private String hqMusicUrl;

    /**
     * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
     */
    @XmlCDATA
    @XmlElement(name = "ThumbMediaId")
    private String thumbMediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicURL() {
        return musicURL;
    }

    public void setMusicURL(String musicURL) {
        this.musicURL = musicURL;
    }

    public String getHqMusicUrl() {
        return hqMusicUrl;
    }

    public void setHqMusicUrl(String hqMusicUrl) {
        this.hqMusicUrl = hqMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
