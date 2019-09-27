package com.shengma.lanjing.beans;

import java.util.List;

public class SouSuoBean {

    /**
     * code : 2000
     * desc : Success
     * result : [{"nickname":"18379382300","userCode":"100001","headImage":"","pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_1?txSecret=8aa56e3d957764d1eb6d8c68466c87c7&txTime=9A7EC800","playUrl":"http://www.curpep.com/live/lanjing_1.flv","status":0},{"nickname":"15870611156","userCode":"100038","headImage":"","pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_100038?txSecret=e88ac88cdaf58c91fc44b6df3b87c858&txTime=9A7EC800","playUrl":"http://www.curpep.com/live/lanjing_100038.flv","status":0}]
     * total : 2
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
         * nickname : 18379382300
         * userCode : 100001
         * headImage :
         * pushUrl : rtmp://56626.livepush.myqcloud.com/live/lanjing_1?txSecret=8aa56e3d957764d1eb6d8c68466c87c7&txTime=9A7EC800
         * playUrl : http://www.curpep.com/live/lanjing_1.flv
         * status : 0
         */

        private String nickname;
        private long id;
        private String headImage;
        private String pushUrl;
        private String playUrl;
        private int status;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
