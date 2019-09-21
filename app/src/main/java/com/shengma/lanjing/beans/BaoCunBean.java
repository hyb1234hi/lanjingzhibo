package com.shengma.lanjing.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


@Entity
public class BaoCunBean {

    @Id(assignable = true)
    private Long id;
    private String sdkAppId;
    private String isBind;
    private String imUserSig;
    private long userid;
    private String nickname;
    private String headImage;
    private int sex;
    private int userLevel;
    private int anchorLevel;
    private int userCode;
    private int duration;
    private int idols;
    private int fans;
    private int auth;   //1是待审核  2是通过 。3是未通过
    private String realName;
    private String idNumber;
    private long roomId;
    private String pushUrl;
    private String playUrl;
    private int age;
    private String qianming;
    private String jiaxiang;
    private String session;
    private int meiyan,meibai,hongrun;


    public int getMeiyan() {
        return meiyan;
    }

    public void setMeiyan(int meiyan) {
        this.meiyan = meiyan;
    }

    public int getMeibai() {
        return meibai;
    }

    public void setMeibai(int meibai) {
        this.meibai = meibai;
    }

    public int getHongrun() {
        return hongrun;
    }

    public void setHongrun(int hongrun) {
        this.hongrun = hongrun;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public String getJiaxiang() {
        return jiaxiang;
    }

    public void setJiaxiang(String jiaxiang) {
        this.jiaxiang = jiaxiang;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getAnchorLevel() {
        return anchorLevel;
    }

    public void setAnchorLevel(int anchorLevel) {
        this.anchorLevel = anchorLevel;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIdols() {
        return idols;
    }

    public void setIdols(int idols) {
        this.idols = idols;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
