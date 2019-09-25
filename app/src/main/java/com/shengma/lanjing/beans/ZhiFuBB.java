package com.shengma.lanjing.beans;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ZhiFuBB {

    private LinearLayout kuang;
    private ImageView im;
    private boolean isA;
    private float monery;

    public float getMonery() {
        return monery;
    }

    public void setMonery(float monery) {
        this.monery = monery;
    }

    public ZhiFuBB(LinearLayout kuang, ImageView im) {
        this.kuang = kuang;
        this.im = im;
    }

    public LinearLayout getKuang() {
        return kuang;
    }

    public void setKuang(LinearLayout kuang) {
        this.kuang = kuang;
    }

    public ImageView getIm() {
        return im;
    }

    public void setIm(ImageView im) {
        this.im = im;
    }

    public boolean isA() {
        return isA;
    }

    public void setA(boolean a) {
        isA = a;
    }
}
