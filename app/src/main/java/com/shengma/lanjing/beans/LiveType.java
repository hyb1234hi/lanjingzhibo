package com.shengma.lanjing.beans;

import java.util.List;

public class LiveType {


    /**
     * code : 2000
     * desc : Success
     * result : [{"type":0,"name":"交友"},{"type":1,"name":"户外"},{"type":2,"name":"校园"},{"type":3,"name":"音乐"}]
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
         * type : 0
         * name : 交友
         */
        private int isXZ;
        private int type;
        private String name;

        public int getIsXZ() {
            return isXZ;
        }

        public void setIsXZ(int isXZ) {
            this.isXZ = isXZ;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
