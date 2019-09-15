package com.shengma.lanjing.beans;

public class SaveBean {


    /**
     * code : 1
     * desc : 操作成功
     * result : {"nickname":"的巴黎欧莱雅洗发","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/65901568514300467.png","sex":0,"signature":null,"year":0,"month":null,"province":null,"city":null,"job":1}
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
         * nickname : 的巴黎欧莱雅洗发
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/65901568514300467.png
         * sex : 0
         * signature : null
         * year : 0
         * month : null
         * province : null
         * city : null
         * job : 1
         */

        private String nickname;
        private String headImage;
        private int sex;
        private String signature;
        private int year;
        private String month;
        private String province;
        private String city;
        private int job;

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
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



        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }





        public int getJob() {
            return job;
        }

        public void setJob(int job) {
            this.job = job;
        }
    }
}
