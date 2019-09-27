package com.shengma.lanjing.beans;

import java.util.List;

public class ZhiBoBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"title":"string","anchorLevel":1,"coverImg":"string","onlineNums":0,"roomId":100034,"pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_100034?txSecret=63a54b47d8ec061346bf1f5a04cb071f&txTime=9A7EC800","playUrl":"rtmp://www.curpep.com/live/lanjing_100034","id":34,"startLight":0,"nickname":"333","headImage":"333","rank":5},{"title":"string6","anchorLevel":17,"coverImg":"string6","onlineNums":0,"roomId":100035,"pushUrl":"rtmp://56626.livepush.myqcloud.com/live/lanjing_100035?txSecret=bb53e20c43538d9b2ac7e442095133be&txTime=9A7EC800","playUrl":"rtmp://www.curpep.com/live/lanjing_100035","id":35,"startLight":0,"nickname":"666","headImage":"666","rank":2}]
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
         * title : string
         * anchorLevel : 1
         * coverImg : string
         * onlineNums : 0
         * roomId : 100034
         * pushUrl : rtmp://56626.livepush.myqcloud.com/live/lanjing_100034?txSecret=63a54b47d8ec061346bf1f5a04cb071f&txTime=9A7EC800
         * playUrl : rtmp://www.curpep.com/live/lanjing_100034
         * id : 34
         * startLight : 0
         * nickname : 333
         * headImage : 333
         * rank : 5
         */

        private String title;
        private int anchorLevel;
        private String coverImg;
        private int onlineNums;
        private int roomId;
        private String pushUrl;
        private String playUrl;
        private long id;
        private int startLight;
        private String nickname;
        private String headImage;
        private int rank;


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

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public int getOnlineNums() {
            return onlineNums;
        }

        public void setOnlineNums(int onlineNums) {
            this.onlineNums = onlineNums;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getStartLight() {
            return startLight;
        }

        public void setStartLight(int startLight) {
            this.startLight = startLight;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
