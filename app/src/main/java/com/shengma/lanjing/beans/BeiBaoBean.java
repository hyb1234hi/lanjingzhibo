package com.shengma.lanjing.beans;

import java.util.List;

public class BeiBaoBean {

    /**
     * code : 2000
     * desc : Success
     * result : [{"id":2,"name":"玫瑰","num":2,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E4%B8%80%E6%9C%B5%E7%8E%AB%E7%91%B0%403x.png"},{"id":7,"name":"么么哒","num":4,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E6%B5%93%E5%94%87%E8%AF%B1%E6%83%91%403x.png"},{"id":13,"name":"在一起","num":1,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E5%9C%A8%E4%B8%80%E8%B5%B7%403x.png"},{"id":16,"name":"拿去花","num":7,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E6%92%92%E5%85%83%E5%AE%9D%403x.png"},{"id":24,"name":"520","num":7,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/520%403x.png"},{"id":30,"name":"追爱兰博基尼","num":2,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E7%BA%A2%E8%89%B2%E8%B7%91%E8%BD%A6%403x.png"},{"id":34,"name":"幸福海盗船","num":2,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E6%B5%B7%E7%9B%97%E8%88%B92%403x.png"},{"id":35,"name":"穿云箭","num":7,"giftUrl":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E7%81%AB%E7%AE%AD.png"}]
     * total : 8
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
         * id : 2
         * name : 玫瑰
         * num : 2
         * giftUrl : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/gifts/%E4%B8%80%E6%9C%B5%E7%8E%AB%E7%91%B0%403x.png
         */

        private int id;
        private String name;
        private int num;
        private String giftUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getGiftUrl() {
            return giftUrl;
        }

        public void setGiftUrl(String giftUrl) {
            this.giftUrl = giftUrl;
        }
    }
}
