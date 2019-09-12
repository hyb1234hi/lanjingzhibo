package com.shengma.lanjing.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class LiaoTianBean implements MultiItemEntity {

    public static final int ONE = 1;
    public static final int TOW = 2;


    private int dengji;
    private String neirong;
    private String color;
    private int type;
    private long userid;
    private String nickname;
    private String headImage;
    public String userInfo;   //观众信息

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getDengji() {
        return dengji;
    }

    public void setDengji(int dengji) {
        this.dengji = dengji;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {

        return type;
    }
}
