package com.github.ming.wechat.client.bean.media;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 永久图文素材
 *
 * @author : Liu Zeming
 * @date : 2019-01-12 09:50
 */
public class WechatMediaImageText {

    /**
     * 标题
     */
    private String title;

    /**
     * 图文消息的封面图片素材id（必须是永久mediaID）
     */
    @JSONField(name = "thumb_media_id")
    private String thumbMediaId;

    /**
     * 作者
     */
    private String author;

    /**
     * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空。如果本字段为没有填写，则默认抓取正文前64个字
     */
    private String digest;

    /**
     * 是否显示封面，0为false，即不显示，1为true，即显示
     */
    @JSONField(name = "show_cover_pic")
    private Integer showCoverPic;

    /**
     * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，
     * 且此处会去除JS,涉及图片url必须来源 "上传图文消息内的图片获取URL"接口获取。外部图片url将被过滤。
     */
    private String content;

    /**
     * 图文消息的原文地址，即点击“阅读原文”后的URL
     */
    @JSONField(name = "content_source_url")
    private String contentSourceUrl;

    /**
     * 是否打开评论，0不打开，1打开
     */
    @JSONField(name = "need_open_comment")
    private Integer needOpenComment;

    /**
     * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    @JSONField(name = "only_fans_can_comment")
    private Integer onlyFansCanComment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Integer showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public Integer getNeedOpenComment() {
        return needOpenComment;
    }

    public void setNeedOpenComment(Integer needOpenComment) {
        this.needOpenComment = needOpenComment;
    }

    public Integer getOnlyFansCanComment() {
        return onlyFansCanComment;
    }

    public void setOnlyFansCanComment(Integer onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment;
    }
}