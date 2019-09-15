package com.shengma.lanjing.beans;

public class MsgWarp {

    private int type;
    private String msg;
    private String temp;

    public MsgWarp() {
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
