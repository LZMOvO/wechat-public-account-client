package com.github.ming.wechat.client.bean.qrcode.request;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * WeChatQrCode
 *
 * @author ZM
 * @date : 2019-02-16 12:28
 */
public class WeChatQrCode {

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     */
    @JSONField(name = "expire_seconds")
    private int expireSeconds;

    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，
     * QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    @JSONField(name = "action_name")
    private String actionName;

    /**
     * 二维码详细信息
     */
    @JSONField(name = "action_info")
    private ActionInfo actionInfo;

    private static class ActionInfo {

        /**
         * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
         */
        @JSONField(name = "scene_id")
        private int sceneId;

        /**
         * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
         */
        @JSONField(name = "scene_str")
        private String sceneStr;

        ActionInfo(int sceneId) {
            this.sceneId = sceneId;
        }

        ActionInfo(String sceneStr) {
            this.sceneStr = sceneStr;
        }

        public int getSceneId() {
            return sceneId;
        }

        public void setSceneId(int sceneId) {
            this.sceneId = sceneId;
        }

        public String getSceneStr() {
            return sceneStr;
        }

        public void setSceneStr(String sceneStr) {
            this.sceneStr = sceneStr;
        }
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void setSceneId(int sceneId) {
        this.actionInfo = new ActionInfo(sceneId);
    }

    public void setSceneStr(String sceneStr) {
        this.actionInfo = new ActionInfo(sceneStr);
    }

    public void setActionInfo(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;
    }
}
