package com.shengma.lanjing.beans;

public class LiWuBoFangBean {

    private String name;
    private String liwuName;
    private String liwuID;
    private String headImage;
    private int num;
    private Long id;
    private String liwuPath;

    public String getLiwuID() {
        return liwuID;
    }

    public void setLiwuID(String liwuID) {
        this.liwuID = liwuID;
    }

    public String getLiwuPath() {
        return liwuPath;
    }

    public void setLiwuPath(String liwuPath) {
        this.liwuPath = liwuPath;
    }

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

    public String getLiwuName() {
        return liwuName;
    }

    public void setLiwuName(String liwuName) {
        this.liwuName = liwuName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
