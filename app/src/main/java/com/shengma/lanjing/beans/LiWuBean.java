package com.shengma.lanjing.beans;

import java.util.List;

public class LiWuBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"id":1,"giftName":"一见钟情","giftMoney":"2.0","giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E4%B8%80%E8%A7%81%E9%92%9F%E6%83%85%403x.png","specialUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/special/1.zip","type":0},{"id":2,"giftName":"玫瑰","giftMoney":"2.0","giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E4%B8%80%E6%9C%B5%E7%8E%AB%E7%91%B0%403x.png","specialUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/special/2.zip","type":0},{"id":3,"giftName":"比心","giftMoney":"2.0","giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/%E6%AF%94%E5%BF%83%403x.png","specialUrl":"","type":0},{"id":4,"giftName":"锤子","giftMoney":"2.0","giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E9%94%A4%E5%AD%90%403x.png","specialUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/special/4.zip","type":0}]
     * total : 40
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
         * id : 1
         * giftName : 一见钟情
         * giftMoney : 2.0
         * giftUrl : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E4%B8%80%E8%A7%81%E9%92%9F%E6%83%85%403x.png
         * specialUrl : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/special/1.zip
         * type : 0
         */

        private int id;
        private String giftName;
        private String giftMoney;
        private String giftUrl;
        private String specialUrl;
        private int type;
        private boolean isA;

        public boolean isA() {
            return isA;
        }

        public void setA(boolean a) {
            isA = a;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getGiftMoney() {
            return giftMoney;
        }

        public void setGiftMoney(String giftMoney) {
            this.giftMoney = giftMoney;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }

        public String getSpecialUrl() {
            return specialUrl;
        }

        public void setSpecialUrl(String specialUrl) {
            this.specialUrl = specialUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
