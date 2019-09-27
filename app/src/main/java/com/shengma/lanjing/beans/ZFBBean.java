package com.shengma.lanjing.beans;

public class ZFBBean {


    /**
     * code : 2000
     * desc : Success
     * result : alipay_sdk=alipay-sdk-java-3.7.4.ALL&app_id=2019092567785601&biz_content=%7B%22out_trade_no%22%3A%221177493609671634945%22%2C%22passback_params%22%3A%22%E5%85%AC%E7%94%A8%E5%9B%9E%E4%BC%A0%E5%8F%82%E6%95%B0%EF%BC%8C%E5%A6%82%E6%9E%9C%E8%AF%B7%E6%B1%82%E6%97%B6%E4%BC%A0%E9%80%92%E4%BA%86%E8%AF%A5%E5%8F%82%E6%95%B0%EF%BC%8C%E5%88%99%E8%BF%94%E5%9B%9E%E7%BB%99%E5%95%86%E6%88%B7%E6%97%B6%E4%BC%9A%E5%9B%9E%E4%BC%A0%E8%AF%A5%E5%8F%82%E6%95%B0%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%93%9D%E9%B2%B8%E8%A7%86%E9%A2%91%E5%85%85%E5%80%BC%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.110.128.4%3A8081%2F%2Fapi%2Fv1%2Falipay%2Fnotify&sign=Xvo5NnKGCO8W4AwBpT9kS%2Ft%2FCVsaA7FdWHdVZFRe0%2FSwlf06vSZfrlrhQWh%2BIt4KT5kujPuei6uJR0TQNKcOkUzV3lFmJ0pQ0k2SvvubpilkUtLGYhexhVlaU6tiWxXzc1mDflTgGNXdz4Pnl6DpdR%2FRDDy2YzEy2O3CNBwki0J92z2Hxvxtb25owiTH2f%2FbgQmA5dWehQpVNTfceuod4SRlwJwlbs9%2F1xf3vBmuAi9tiW2PNknsOHiBbtcLwSJi%2BmlPk1P1DGrR%2Fq9vAbuUtBSwGEupSI9gTt8UcnSB6JeTjBoeGy04XYmQRVfGNflQzcvMisjgWJ5NIRA%2FOeKLeg%3D%3D&sign_type=RSA2&timestamp=2019-09-27+16%3A02%3A07&version=1.0
     * total : 0
     */

    private int code;
    private String desc;
    private String result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
