package com.shengma.lanjing.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

public class ToastUtils {


    public static void showInfo(Activity context, String msg){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast tastyToast = TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.INFO);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
            }
        });
    }

    public static void showError(Activity context, String msg){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast tastyToast = TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.ERROR);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();
            }
        });
    }

}
