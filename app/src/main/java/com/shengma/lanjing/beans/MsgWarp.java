package com.shengma.lanjing.beans;

public class MsgWarp {

    private int type;
    private String msg;
    private String temp;
    private String num="1";

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public MsgWarp() {
    }

    public MsgWarp(int type, String msg, String temp, String num) {
        this.type = type;
        this.msg = msg;
        this.temp = temp;
        this.num = num;
    }

    public MsgWarp(int type, String msg, String temp) {
        this.type = type;
        this.msg = msg;
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public MsgWarp(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
