package com.shengma.lanjing.beans;

public class ZHUYEBean {


    /**
     * code : 2000
     * desc : Success
     * result : {"id":29,"nickname":"的巴黎欧莱雅洗发","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png","sex":0,"userLevel":5,"anchorLevel":1,"userCode":100029,"duration":12151,"idols":1,"fans":1,"auth":2,"realName":"Gig","idNumber":"666654678876666678"}
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
         * nickname : 的巴黎欧莱雅洗发
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png
         * sex : 0
         * userLevel : 5
         * anchorLevel : 1
         * userCode : 100029
         * duration : 12151
         * idols : 1
         * fans : 1
         * auth : 2
         * realName : Gig
         * idNumber : 666654678876666678
         */

        private int id;
        private String nickname;
        private String headImage;
        private int sex;
        private int userLevel;
        private int anchorLevel;
        private int userCode;
        private int duration;
        private int idols;
        private int fans;
        private int auth;
        private String realName;
        private String idNumber;

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

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(int anchorLevel) {
            this.anchorLevel = anchorLevel;
        }

        public int getUserCode() {
            return userCode;
        }

        public void setUserCode(int userCode) {
            this.userCode = userCode;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getIdols() {
            return idols;
        }

        public void setIdols(int idols) {
            this.idols = idols;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public int getAuth() {
            return auth;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
    }
}
