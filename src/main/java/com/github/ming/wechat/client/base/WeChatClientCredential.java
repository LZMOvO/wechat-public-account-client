package com.github.ming.wechat.client.base;

/**
 * WeChatClientCredential
 *
 * @author : ZM
 * @date : 2019-12-24 15:04:19
 */
public class WeChatClientCredential {

    private WeChatCredentialHolder credentialHolder;

    public WeChatCredentialHolder getCredentialHolder() {
        return credentialHolder;
    }

    public void setCredentialHolder(WeChatCredentialHolder credentialHolder) {
        this.credentialHolder = credentialHolder;
    }
}
