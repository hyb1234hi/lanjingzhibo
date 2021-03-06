package com.shengma.lanjing.beans;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;



@Entity
public class LiwuPathBean implements Comparable<LiwuPathBean>{

    @Id(assignable = true)
    private Long id;
    private String path;
    private Long sid;
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    @Override
    public int compareTo(LiwuPathBean o) {

        return this.getUid() - o.getUid();
    }
}
