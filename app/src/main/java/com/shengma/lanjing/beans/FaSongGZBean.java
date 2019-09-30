package com.shengma.lanjing.beans;

import java.util.List;

public class FaSongGZBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"id":29,"nickname":"你好来咯","sex":1,"headImage":"https://img-1259669722.cos.ap-guangzhou.myqcloud.com/images/25871568514860813.png"}]
     * total : 1
     */


    private List<ResultBean> result;



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
        private int xingguang;
        private String headImage;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getXingguang() {
            return xingguang;
        }

        public void setXingguang(int xingguang) {
            this.xingguang = xingguang;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }
    }
}
