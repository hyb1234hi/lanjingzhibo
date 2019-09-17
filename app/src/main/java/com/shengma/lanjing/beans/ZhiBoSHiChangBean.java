package com.shengma.lanjing.beans;

import java.util.List;

public class ZhiBoSHiChangBean {


    /**
     * code : 2000
     * desc : Success
     * result : [{"day":"2019.08.23","minutes":0},{"day":"2019.08.27","minutes":0},{"day":"2019.08.28","minutes":495},{"day":"2019.08.29","minutes":1663},{"day":"2019.08.30","minutes":1662},{"day":"2019.08.31","minutes":1229}]
     * total : 12
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
         * day : 2019.08.23
         * minutes : 0
         */

        private String day;
        private int minutes;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }
    }
}
