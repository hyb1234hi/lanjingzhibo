package com.shengma.lanjing.beans;

public class LogingBe {


    /**
     * code : 2000
     * desc : Success
     * result : {"sdkAppId":1400240418,"isBind":1,"imUserSig":"eJyrVgrxCdZLrSjILEpVsjIytTQyMNABi5WlFilZKRnpGShB*MUp2YkFBZkpSlaGJgYGRiYGJoYW\nEJnMlNS8ksy0TIgGS5j6zHQgN9jM1alMPy*lwMk11DTbw8KgMMfQ2DA-vKwwPyfcN8ukJMnfq9gp\nPLLUNN8WqrEkMxfoFENTM3NTQ0MzY*NaABY2L04_"}
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
         * sdkAppId : 1400240418
         * isBind : 1
         * imUserSig : eJyrVgrxCdZLrSjILEpVsjIytTQyMNABi5WlFilZKRnpGShB*MUp2YkFBZkpSlaGJgYGRiYGJoYW
         EJnMlNS8ksy0TIgGS5j6zHQgN9jM1alMPy*lwMk11DTbw8KgMMfQ2DA-vKwwPyfcN8ukJMnfq9gp
         PLLUNN8WqrEkMxfoFENTM3NTQ0MzY*NaABY2L04_
         */

        private String sdkAppId;
        private String isBind;
        private String imUserSig;

        public String getSdkAppId() {
            return sdkAppId;
        }

        public void setSdkAppId(String sdkAppId) {
            this.sdkAppId = sdkAppId;
        }

        public String getIsBind() {
            return isBind;
        }

        public void setIsBind(String isBind) {
            this.isBind = isBind;
        }

        public String getImUserSig() {
            return imUserSig;
        }

        public void setImUserSig(String imUserSig) {
            this.imUserSig = imUserSig;
        }
    }
}
