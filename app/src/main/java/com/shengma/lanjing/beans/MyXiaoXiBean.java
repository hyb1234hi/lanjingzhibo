package com.shengma.lanjing.beans;

import java.util.List;

public class MyXiaoXiBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"title":"aa","content":"test 1","createTime":"2019-08-29 12:01:54"},{"title":"bb","content":"test2","createTime":"2019-08-29 12:02:02"}]
     * total : 0
     */

    private int code;
    private String desc;
    private int total;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title : aa
         * content : test 1
         * createTime : 2019-08-29 12:01:54
         */

        private String title;
        private String content;
        private String createTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
