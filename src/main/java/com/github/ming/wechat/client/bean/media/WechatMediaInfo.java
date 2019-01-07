package com.github.ming.wechat.client.bean.media;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * WechatMedia
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 12:11
 */
public class WechatMediaInfo {

    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
     */
    private String type;

    /**
     * 媒体文件上传后，获取标识
     */
    @JSONField(name = "media_id")
    private String mediaId;

    /**
     * 媒体文件上传时间戳
     */
    @JSONField(name = "created_at")
    private Integer createAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}
