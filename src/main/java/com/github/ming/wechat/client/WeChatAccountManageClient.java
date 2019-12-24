package com.github.ming.wechat.client;

import com.github.ming.wechat.client.apiurl.WeChatApiUrls;
import com.github.ming.wechat.client.bean.qrcode.request.WeChatQrCode;
import com.github.ming.wechat.client.bean.qrcode.response.WeChatQrCodeResult;
import com.github.ming.wechat.client.exception.WeChatException;

/**
 * 账号管理
 *
 * @author ZM
 * @date : 2019-02-16 12:19
 */
public class WeChatAccountManageClient {

    private WeChatCredentialHolder credentialHolder;

    public WeChatAccountManageClient(WeChatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }

    /**
     * 生成带参数的二维码
     *
     * @param wechatQrCode 参数
     * @return 请求结果
     */
    public WeChatQrCodeResult createQrCode(WeChatQrCode wechatQrCode) throws WeChatException {
        if (wechatQrCode == null) {
            throw new WeChatException("二维码参数为空");
        }
        String result = WeChatRequest.post(WeChatApiUrls.CREATE_QRCODE_URL, wechatQrCode, credentialHolder);
        return WeChatResponse.result2Bean(result, WeChatQrCodeResult.class);
    }
}
