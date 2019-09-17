package com.shengma.lanjing.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


@Entity
public class YongHuListBean {

    @Id(assignable = true)
    private Long id;
    private String name;
    private String headImage;
    private int sex;
    private int dengji;
    private int type;//0是普通观众。1是管理员，2是被禁言
    private int jingbi;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getDengji() {
        return dengji;
    }

    public void setDengji(int dengji) {
        this.dengji = dengji;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getJingbi() {
        return jingbi;
    }

    public void setJingbi(int jingbi) {
        this.jingbi = jingbi;
    }
}
