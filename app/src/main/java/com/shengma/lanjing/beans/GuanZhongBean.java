package com.shengma.lanjing.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class GuanZhongBean {

    @Id(assignable = true)
    private Long id;
    private String nickname;
    private String headImage;
    private String xingguang;
    private int sex;
    private int level;
    private boolean isJY;
    private boolean isGLY;


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isJY() {
        return isJY;
    }

    public void setJY(boolean JY) {
        isJY = JY;
    }

    public boolean isGLY() {
        return isGLY;
    }

    public void setGLY(boolean GLY) {
        isGLY = GLY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getXingguang() {
        return xingguang;
    }

    public void setXingguang(String xingguang) {
        this.xingguang = xingguang;
    }
}
