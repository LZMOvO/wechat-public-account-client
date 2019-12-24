package com.github.ming.wechat.client.bean.user.request;

/**
 * 配合创建标签接口提交参数格式构造的bean
 *
 * @author ZM
 * @date : 2018-12-20 01:39
 */
public class UpdateWechatUserTag {

    private UseTag tag ;

    public UpdateWechatUserTag(int tagId, String tagName) {
        tag = new UseTag();
        tag.setId(tagId);
        tag.setName(tagName);
    }

    private static class UseTag {

        /**
         * 标签id
         */
        private Integer id;

        /**
         * 标签名（30个字符以内）
         */
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

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
