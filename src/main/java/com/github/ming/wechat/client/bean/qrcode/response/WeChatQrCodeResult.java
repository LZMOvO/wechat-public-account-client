package com.github.ming.wechat.client.bean.qrcode.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * WeChatQrCodeResult
 *
 * @author ZM
 * @date : 2019-02-16 15:35
 */
public class WeChatQrCodeResult {

    /**
     * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码
     */
    private String ticket;

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）
     */
    @JSONField(name = "expire_seconds")
    private int expireSeconds;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
