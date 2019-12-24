package com.github.ming.wechat.msgevent.bean;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信回复图文消息
 *
 * @author ZM
 * @date : 2019-01-01 02:36
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WeChatReplyArticle {

    /**
     * 图文消息标题
     */
    @XmlCDATA
    @XmlElement(name = "Title")
    private String title;

    /**
     * 图文消息描述
     */
    @XmlCDATA
    @XmlElement(name = "Description")
    private String description;

    /**
     * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
     */
    @XmlCDATA
    @XmlElement(name = "PicUrl")
    private String picUrl;

    /**
     * 点击图文消息跳转链接
     */
    @XmlCDATA
    @XmlElement(name = "Url")
    private String url;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
