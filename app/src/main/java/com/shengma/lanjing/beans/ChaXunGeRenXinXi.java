package com.shengma.lanjing.beans;

public class ChaXunGeRenXinXi {


    /**
     * code : 2000
     * desc : Success
     * result : {"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/86531568517955304.png","nickname":"瑞瞳测试","sex":2,"anchorLevel":1,"userLevel":1,"id":600046,"fans":0,"idols":0,"starLight":0}
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
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/86531568517955304.png
         * nickname : 瑞瞳测试
         * sex : 2
         * anchorLevel : 1
         * userLevel : 1
         * id : 600046
         * fans : 0
         * idols : 0
         * starLight : 0.0
         */

        private String headImage;
        private String nickname;
        private int sex;
        private int anchorLevel;
        private int userLevel;
        private int id;
        private int fans;
        private int idols;
        private double starLight;
        private long rank;

        public long getRank() {
            return rank;
        }

        public void setRank(long rank) {
            this.rank = rank;
        }

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

        public double getStarLight() {
            return starLight;
        }

        public void setStarLight(double starLight) {
            this.starLight = starLight;
        }
    }
}
