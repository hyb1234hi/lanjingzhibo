package com.shengma.lanjing.beans;

import java.util.List;

public class JieShuPkBean {

    private int code;
    private String desc;
    private int total;
    private JieShuPkBean.ResultBean result;

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

    public JieShuPkBean.ResultBean getResult() {
        return result;
    }

    public void setResult(JieShuPkBean.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String otherGift;//对方房间礼物数
        private String ownGift;//本房间礼物数

        public String getOtherGift() {
            return otherGift;
        }

        public void setOtherGift(String otherGift) {
            this.otherGift = otherGift;
        }

        public String getOwnGift() {
            return ownGift;
        }

        public void setOwnGift(String ownGift) {
            this.ownGift = ownGift;
        }
    }
}
