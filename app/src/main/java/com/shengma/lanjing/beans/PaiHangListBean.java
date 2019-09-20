package com.shengma.lanjing.beans;

import java.util.List;

public class PaiHangListBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"nickname":"test","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/27931568637890420.png","sex":1,"level":24,"income":333364},{"nickname":"18379382300","headImage":"","sex":1,"level":23,"income":176827},{"nickname":"666","headImage":"666","sex":0,"level":17,"income":66660},{"nickname":"你好来咯","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png","sex":2,"level":7,"income":4368},{"nickname":"瑞瞳测试","headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/86531568517955304.png","sex":2,"level":1,"income":23},{"nickname":"333","headImage":"333","sex":0,"level":1,"income":0},{"nickname":"15870611156","headImage":"","sex":0,"level":1,"income":0}]
     * total : 7
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
         * nickname : test
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/27931568637890420.png
         * sex : 1
         * level : 24
         * income : 333364
         */

        private String nickname;
        private String headImage;
        private int sex;
        private int level;
        private int income;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIncome() {
            return income;
        }

        public void setIncome(int income) {
            this.income = income;
        }
    }
}
