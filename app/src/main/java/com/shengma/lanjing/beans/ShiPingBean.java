package com.shengma.lanjing.beans;

import java.util.List;

public class ShiPingBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"img":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/71091569238287927.png","url":"https://video-1259669722.cos.ap-guangzhou.myqcloud.com/videos/6811569238291556.mp4","type":1,"userLevel":31,"title":"看见了","nums":null},{"img":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/3931569238106980.png","url":"https://video-1259669722.cos.ap-guangzhou.myqcloud.com/videos/63121569238110126.mp4","type":1,"userLevel":31,"title":"途径","nums":null},{"img":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/27931568637890420.png","url":"https://video-1259669722.cos.ap-guangzhou.myqcloud.com/videos/23931568878829307.mp4","type":1,"userLevel":31,"title":"小视屏","nums":null},{"img":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/42441565951329672.png","url":"https://video-1259669722.cos.ap-guangzhou.myqcloud.com/videos/61031565951330088.mp4","type":1,"userLevel":31,"title":"测试1","nums":null}]
     * total : 4
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
         * img : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/71091569238287927.png
         * url : https://video-1259669722.cos.ap-guangzhou.myqcloud.com/videos/6811569238291556.mp4
         * type : 1
         * userLevel : 31
         * title : 看见了
         * nums : null
         */

        private String img;
        private String url;
        private int type;
        private int userLevel;
        private String title;
        private int nums;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
