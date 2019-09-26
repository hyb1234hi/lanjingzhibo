package com.shengma.lanjing.beans;

import java.util.List;

public class YongHuPaiHang {


    /**
     * code : 2000
     * desc : Success
     * result : [{"id":2,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/111569415380049.png","total":1003985.2,"sex":1,"userLevel":31,"nickname":"genwei"},{"id":33,"headImage":"777.img","total":10732,"sex":1,"userLevel":16,"nickname":"777"},{"id":1,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/53171568988339222.png","total":210,"sex":1,"userLevel":31,"nickname":"111333"},{"id":3,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/31161566193799100.jpeg","total":42,"sex":0,"userLevel":1,"nickname":"123456"},{"id":29,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png","total":14,"sex":1,"userLevel":31,"nickname":"你好来咯"}]
     * total : 0
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
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/111569415380049.png
         * total : 1003985.2
         * sex : 1
         * userLevel : 31
         * nickname : genwei
         */

        private int id;
        private String headImage;
        private double total;
        private int sex;
        private int userLevel;
        private String nickname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
