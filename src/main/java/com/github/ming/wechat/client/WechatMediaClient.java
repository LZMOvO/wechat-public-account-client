package com.github.ming.wechat.client;

import com.alibaba.fastjson.JSONObject;
import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.media.WechatMaterialVideoDesc;
import com.github.ming.wechat.client.bean.media.WechatMediaImageText;
import com.github.ming.wechat.client.bean.media.WechatMediaInfo;
import com.github.ming.wechat.client.exception.WechatException;
import com.github.ming.wechat.client.type.media.MediaType;

import java.io.File;
import java.util.List;

/**
 * 素材管理
 *
 * @author : Liu Zeming
 * @date : 2019-01-07 06:48
 */
public class WechatMediaClient {

    /**
     * 1m字节数
     */
    private static final int ONE_M_BYTE = 1048576;

    /**
     * 2m字节数
     */
    private static final int TWO_M_BYTE = 1048576 * 2;

    /**
     * 10m字节数
     */
    private static final int TEM_M_BYTE = 1048576 * 10;

    /**
     * 10m字节数
     */
    private static final int SIXTY_FOUR_KB = 1024 * 64;

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
     *             视频（video）：10MB，支持MP4格式；缩略图（thumb）：64KB，支持JPG格式；
     *             缩略图（thumb）：64KB，支持JPG格式；
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @return 上传结果
     */
    public WechatMediaInfo uploadMedia(File file, String type) throws WechatException {
        if (file == null || file.length() == 0) {
            throw new WechatException("上传的素材为空");
        }
        if (!MediaType.findType(type)) {
            throw new WechatException("上传的素材类型有误");
        }
        if (MediaType.IMAGE.getType().equals(type) && file.length() > TWO_M_BYTE) {
            throw new WechatException("图片大小超过2M");
        }
        if (MediaType.VOICE.getType().equals(type) && file.length() > TWO_M_BYTE) {
            throw new WechatException("语音大小超过2M");
        }
        if (MediaType.VIDEO.getType().equals(type) && file.length() > TEM_M_BYTE) {
            throw new WechatException("视频大小超过10M");
        }
        if (MediaType.THUMB.getType().equals(type) && file.length() > SIXTY_FOUR_KB) {
            throw new WechatException("缩略图大小超过64KB");
        }
        String result = WechatRequest.upload(WechatApiUrls.MEDIA_UPLOAD_URL.replace("TYPE", type), file, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMediaInfo.class);
    }

    /*---- 新增永久素材 ----*/

    /**
     * 新增永久图文素材
     *
     * @param imageTextList 图文list
     * @return 素材id
     */
    public String uploadPermanentMedia(List<WechatMediaImageText> imageTextList) throws WechatException {
        if (imageTextList == null || imageTextList.size() == 0) {
            throw new WechatException("上传的素材为空");
        }
        JSONObject params = new JSONObject(3);
        params.put("articles", imageTextList);
        String result = WechatRequest.post(WechatApiUrls.PERMANENT_MEDIA_UPLOAD_URL, params.toJSONString(), credentialHolder);
        return WechatResponse.result2Bean(result, WechatMediaInfo.class).getMediaId();
    }

    /**
     * 上传图文消息内的图片获取URL
     * 本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下
     *
     * @param file 图片file
     * @return 图片url
     */
    public String uploadMediaImg(File file) throws WechatException {
        if (file == null || file.length() == 0) {
            throw new WechatException("上传的素材为空");
        }
        if (file.length() > ONE_M_BYTE) {
            throw new WechatException("图片大小超过1M");
        }
        String result = WechatRequest.upload(WechatApiUrls.MEDIA_UPLOADIMG_UPLOAD_URL, file, credentialHolder);
        return WechatResponse.result2Bean(result, JSONObject.class).getString("url");
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
        if (file == null || file.length() == 0) {
            throw new WechatException("上传的素材为空");
        }
        if (!MediaType.findType(type)) {
            throw new WechatException("上传的素材类型有误");
        }
        if (MediaType.VIDEO.getType().equals(type)) {
            throw new WechatException("请使用 uploadMaterialVideo 接口上传视频");
        }
        if (MediaType.VOICE.getType().equals(type) && file.length() > TWO_M_BYTE) {
            throw new WechatException("语音大小超过2M");
        }
        if (MediaType.IMAGE.getType().equals(type) && file.length() > TWO_M_BYTE) {
            throw new WechatException("图片大小超过2M");
        }
        if (MediaType.THUMB.getType().equals(type) && file.length() > SIXTY_FOUR_KB) {
            throw new WechatException("缩略图大小超过64KB");
        }
        String result = WechatRequest.upload(WechatApiUrls.MATERIAL_UPLOAD_URL.replace("TYPE", type), file, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMediaInfo.class);
    }

    /**
     * 新增永久视频类型素材，不可以上传其他类型
     *
     * @param file 视频（video）：10MB，支持MP4格式；缩略图（thumb）：64KB，支持JPG格式；
     * @param desc type=video时，desc必传；type为其他类型时，传空即可
     * @return 上传结果
     */
    public WechatMediaInfo uploadMaterialVideo(File file, WechatMaterialVideoDesc desc) throws WechatException {
        if (file == null || file.length() == 0) {
            throw new WechatException("上传的素材为空");
        }
        if (desc == null) {
            throw new WechatException("视频需要提交的表单为空");
        }
        if (file.length() > TEM_M_BYTE) {
            throw new WechatException("视频大小超过10M");
        }
        String result = WechatRequest.uploadMaterialVideo(WechatApiUrls.MATERIAL_UPLOAD_URL.replace("TYPE",
                MediaType.VIDEO.getType()), file, desc, credentialHolder);
        return WechatResponse.result2Bean(result, WechatMediaInfo.class);
    }
}
