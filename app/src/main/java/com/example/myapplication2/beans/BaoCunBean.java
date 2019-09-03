package com.example.myapplication2.beans;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


@Entity
public class BaoCunBean {

    @Id(assignable = true)
    private Long id;
    private String sdkAppId;
    private String isBind;
    private String imUserSig;


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
