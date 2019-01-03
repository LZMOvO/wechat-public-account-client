package com.github.ming.wechat.msgevent.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 回复视频消息
 *
 * @author : Liu Zeming
 * @date : 2019-01-01 01:08
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WechatReplyVideo {

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
}
