package com.shengma.lanjing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XueTiaoView extends View {
    private int w;

    public XueTiaoView(Context context) {
        super(context);
    }

    public XueTiaoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XueTiaoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setW(int w){
        this.w=w;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
