package com.shengma.lanjing.beans;

public class KaiBoBean {


    /**
     * code : 2000
     * desc : Success
     * result : {"roomId":100029,"pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_100029?txSecret=eee325fe2253f40295647eaa22ecfc6b&txTime=9A7EC800","playUrl":"http://www.curpep.com/live/lanjing_100029.flv"}
     * total : 0
     */

    private int code;
    private String desc;
    private ResultBean result;
    private int total;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class ResultBean {
        /**
         * roomId : 100029
         * pushUrl : rtmp://56626.livepush.myqcloud.com/live/lanjing_100029?txSecret=eee325fe2253f40295647eaa22ecfc6b&txTime=9A7EC800
         * playUrl : http://www.curpep.com/live/lanjing_100029.flv
         */

        private long roomId;
        private String pushUrl;
        private String playUrl;

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(long roomId) {
            this.roomId = roomId;
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
    }
}
