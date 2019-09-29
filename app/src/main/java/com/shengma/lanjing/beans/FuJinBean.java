package com.shengma.lanjing.beans;

import java.util.List;

public class FuJinBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"distance":86,"coverImg":" ","title":" ","anchorLevel":1,"roomId":100001,"pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_1?txSecret=8aa56e3d957764d1eb6d8c68466c87c7&txTime=9A7EC800","playUrl":"http://www.curpep.com/live/lanjing_1.flv"}]
     * total : 1
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
         * distance : 86
         * coverImg :
         * title :
         * anchorLevel : 1
         * roomId : 100001
         * pushUrl : rtmp://56626.livepush.myqcloud.com/live/lanjing_1?txSecret=8aa56e3d957764d1eb6d8c68466c87c7&txTime=9A7EC800
         * playUrl : http://www.curpep.com/live/lanjing_1.flv
         */
        private Long id;
        private int distance;
        private String coverImg;
        private String title;
        private int anchorLevel;
        private int roomId;
        private String pushUrl;
        private String playUrl;
        private String playSafeUrl;

        public String getPlaySafeUrl() {
            return playSafeUrl;
        }

        public void setPlaySafeUrl(String playSafeUrl) {
            this.playSafeUrl = playSafeUrl;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(int anchorLevel) {
            this.anchorLevel = anchorLevel;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
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
