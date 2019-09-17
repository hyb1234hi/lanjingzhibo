package com.shengma.lanjing.beans;

import java.util.List;

public class FenSiBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"id":29,"nickname":"你好来咯","sex":1,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png"}]
     * total : 1
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
         * id : 29
         * nickname : 你好来咯
         * sex : 1
         * headImage : https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png
         */

        private int id;
        private String nickname;
        private int sex;
        private String headImage;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }
    }
}
