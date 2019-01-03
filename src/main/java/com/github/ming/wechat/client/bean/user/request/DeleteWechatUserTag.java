package com.github.ming.wechat.client.bean.user.request;

/**
 * DeleteUserTag
 *
 * @author : Liu Zeming
 * @date : 2018-12-27 00:43
 */
public class DeleteWechatUserTag {

    private UseTag tag;

    public DeleteWechatUserTag(int tagId) {
        tag = new UseTag();
        tag.setId(tagId);
    }

    private static class UseTag {

        /**
         * 标签id
         */
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public UseTag getTag() {
        return tag;
    }

    public void setTag(UseTag tag) {
        this.tag = tag;
    }
}
