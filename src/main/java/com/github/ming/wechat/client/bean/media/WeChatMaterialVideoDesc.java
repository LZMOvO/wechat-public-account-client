package com.github.ming.wechat.client.bean.media;

/**
 * 永久素材中，视频素材提交的表单内容
 *
 * @author ZM
 * @date : 2019-01-13 23:11
 */
public class WeChatMaterialVideoDesc {

    /**
     * 视频素材的标题
     */
    private String title;

    /**
     * 视频素材的描述
     */
    private String introduction;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
