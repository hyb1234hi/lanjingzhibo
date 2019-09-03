package com.example.myapplication2.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class ControlScrollViewPager extends ViewPager {



    public ControlScrollViewPager(Context context) {
        super(context);
    }

    public ControlScrollViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
