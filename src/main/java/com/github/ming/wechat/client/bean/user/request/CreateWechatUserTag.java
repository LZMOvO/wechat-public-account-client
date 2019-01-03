package com.github.ming.wechat.client.bean.user.request;

/**
 * 配合创建标签接口提交参数格式构造的bean
 *
 * @author : Liu Zeming
 * @date : 2018-12-20 01:39
 */
public class CreateWechatUserTag {

    private UseTag tag;

    public CreateWechatUserTag(String tagName) {
        tag = new UseTag();
        tag.setName(tagName);
    }

    private static class UseTag {

        /**
         * 标签名（30个字符以内）
         */
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public UseTag getTag() {
        return tag;
    }

    public void setTag(UseTag tag) {
        this.tag = tag;
    }
}
