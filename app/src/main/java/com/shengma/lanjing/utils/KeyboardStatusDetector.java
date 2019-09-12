package com.shengma.lanjing.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;

public class KeyboardStatusDetector {

    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 200;
    private KeyboardVisibilityListener mVisibilityListener;

    boolean keyboardVisible = false;
    private int heightPixels;

    public KeyboardStatusDetector(Activity context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        //int widthPixels = outMetrics.widthPixels;
        heightPixels = outMetrics.heightPixels;

    }

    public KeyboardStatusDetector registerFragment(Fragment f) {
        return registerView(f.getView());
    }

    public KeyboardStatusDetector registerActivity(Activity a) {
        return registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public KeyboardStatusDetector registerView(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);
                //int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                int heightDiff = v.getRootView().getHeight() - r.bottom;
                //Log.d("ZhiBoActivity键盘", v.getRootView().getHeight() +"   "+ r.bottom +"  "+ r.top);
               // Log.d("ZhiBoActivity键盘", "heightDiff:" + heightDiff);
               // Log.d("ZhiBoActivity键盘", "keyboardVisible:" + keyboardVisible);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT && heightDiff!=heightPixels) { // if more than 100 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(true, heightDiff);
                        }
                    }else {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false, heightDiff);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (mVisibilityListener != null) {
                            mVisibilityListener.onVisibilityChanged(false, heightDiff);
                        }
                    }
                }
            }
        });

        return this;
    }

    public KeyboardStatusDetector setVisibilityListener(KeyboardVisibilityListener listener) {
        mVisibilityListener = listener;
        return this;
    }

    public interface KeyboardVisibilityListener {
        void onVisibilityChanged(boolean keyboardVisible, int heightDiff);
    }
}