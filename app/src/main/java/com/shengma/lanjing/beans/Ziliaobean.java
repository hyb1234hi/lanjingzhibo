package com.shengma.lanjing.beans;

public class Ziliaobean {


    /**
     * code : 1
     * desc : 操作成功
     * result : {"id":29,"nickname":"你好来咯","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png","sex":1,"signature":"你每次吭哧吭哧","isAuthentication":1,"year":0,"month":1,"province":"1","city":"兴安盟","job":1}
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
         * id : 29
         * nickname : 你好来咯
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png
         * sex : 1
         * signature : 你每次吭哧吭哧
         * isAuthentication : 1
         * year : 0
         * month : 1
         * province : 1
         * city : 兴安盟
         * job : 1
         */

        private int id;
        private String nickname;
        private String headImage;
        private int sex;
        private String signature;
        private int isAuthentication;
        private int year;
        private int month;
        private String province;
        private String city;
        private int job;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getIsAuthentication() {
            return isAuthentication;
        }

        public void setIsAuthentication(int isAuthentication) {
            this.isAuthentication = isAuthentication;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getJob() {
            return job;
        }

        public void setJob(int job) {
            this.job = job;
        }
    }
}
