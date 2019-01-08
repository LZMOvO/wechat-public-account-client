package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.media.WechatMediaInfo;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.client.type.media.MediaType;

import java.io.File;

/**
 * 素材管理
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 06:48
 */
public class WechatMediaClient {

    private WechatCredentialHolder credentialHolder;

    public WechatMediaClient(WechatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /**
     * 新增临时素材
     * 媒体文件在微信后台保存时间为3天，即3天后media_id失效
     *
     * @param file 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式；
     *             语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式；
     *             视频（video）：10MB，支持MP4格式；缩略图（thumb）：64KB，支持JPG格式
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return 上传结果
     */
    public WechatMediaInfo mediaUpload(File file, String type) throws WechatException {
        if (file == null || file.length() == 0) {
            throw new WechatException("上传的素材为空");
        }
        if (!MediaType.findMsgTpye(type)) {
            throw new WechatException("上传的素材类型有误");
        }
        String result = WechatRequest.upload(WechatApiUrls.MEDIA_UPLOAD_URL.replace("TYPE", type), file, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMediaInfo.class);
    }

}
