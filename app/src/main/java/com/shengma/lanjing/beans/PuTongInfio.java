package com.shengma.lanjing.beans;

public class PuTongInfio {

    /**
     * code : 2000
     * desc : Success
     * result : {"headImage":"","nickname":"18379382300","sex":1,"anchorLevel":24,"userLevel":26,"id":1,"fans":1,"idols":1,"total":12}
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
         * headImage :
         * nickname : 18379382300
         * sex : 1
         * anchorLevel : 24
         * userLevel : 26
         * id : 1
         * fans : 1
         * idols : 1
         * total : 12
         */

        private String headImage;
        private String nickname;
        private int sex;
        private int anchorLevel;
        private int userLevel;
        private int id;
        private int fans;
        private int idols;
        private int total;

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAnchorLevel() {
            return anchorLevel;
        }

        public void setAnchorLevel(int anchorLevel) {
            this.anchorLevel = anchorLevel;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public int getIdols() {
            return idols;
        }

        public void setIdols(int idols) {
            this.idols = idols;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
