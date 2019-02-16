package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WechatApiUrls;
import com.github.ming.wechat.client.bean.qrcode.request.WechatQrCode;
import com.github.ming.wechat.client.bean.qrcode.response.WechatQrCodeResult;
import com.github.ming.wechat.client.exception.WechatException;

/**
 * 账号管理
 *
 * @author : Liu Zeming
 * @date : 2019-02-16 12:19
 */
public class WechatAcountManageClient {

    private WechatCredentialHolder credentialHolder;

    public WechatAcountManageClient(WechatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /**
     * 生成带参数的二维码
     *
     * @param wechatQrCode 参数
     * @return 请求结果
     */
    public WechatQrCodeResult createQrCode(WechatQrCode wechatQrCode) throws WechatException {
        if (wechatQrCode == null) {
            throw new WechatException("二维码参数为空");
        }
        String result = WechatRequest.post(WechatApiUrls.CREATE_QRCODE_URL, wechatQrCode, credentialHolder);
        return WechatResponse.result2Bean(result, WechatQrCodeResult.class);
    }

}
