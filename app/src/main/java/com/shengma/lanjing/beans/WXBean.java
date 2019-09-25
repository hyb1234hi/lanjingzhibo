package com.shengma.lanjing.beans;

import com.google.gson.annotations.SerializedName;

public class WXBean {


    /**
     * code : 2000
     * desc : Success
     * result : {"package":"Sign=WXPay","appid":"wxa118e3198ef780bd","sign":"C5BBE6143D2274AD5D0B25E0A0074437","partnerid":"1556939391","prepayid":"wx251852099822741246a15bff1868141000","noncestr":"Ztm8u5LNJpUzqDEO","timestamp":"1569408730"}
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
         * package : Sign=WXPay
         * appid : wxa118e3198ef780bd
         * sign : C5BBE6143D2274AD5D0B25E0A0074437
         * partnerid : 1556939391
         * prepayid : wx251852099822741246a15bff1868141000
         * noncestr : Ztm8u5LNJpUzqDEO
         * timestamp : 1569408730
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
