package com.github.ming.wechat.event.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 回复语音消息
 *
 * @author : Liu Zeming
 * @date : 2019-01-01 01:08
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WechatReplyVoice {

    /**
     * 通过素材管理中的接口上传多媒体文件，得到的id
     */
    @XmlCDATA
    @XmlElement(name = "MediaId")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
