package com.example.myapplication2;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import android.view.WindowManager;

import com.example.myapplication2.dialogall.CommonData;
import com.example.myapplication2.dialogall.CommonDialogService;
import com.example.myapplication2.dialogall.ToastUtils;

import com.tencent.bugly.Bugly;


import org.jetbrains.annotations.NotNull;

import java.io.File;


/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {


    public static MyApplication myApplication;


    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"ruitongzipyqt";
    public static final String SDPATH2 = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"ruitongyqt";




    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
       // BoxStore mBoxStore = MyObjectBox.builder().androidContext(this).build();

        Bugly.init(getApplicationContext(), "dbdf648da8", false);

      //全局dialog
        this.registerActivityLifecycleCallbacks(this);//注册
        CommonData.applicationContext = this;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
        Intent dialogservice = new Intent(this, CommonDialogService.class);
        startService(dialogservice);



    }



    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else{
            CommonData.mNowContext = activity;
        }

      //  Intent intent = new Intent(activity, MyService.class);
      //  bindService(intent, serviceConnection,  Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onActivityStarted(@NotNull Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(activity.getParent()!=null){
            CommonData.mNowContext = activity.getParent();
        }else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityPaused(@NotNull Activity activity) {
        ToastUtils.getInstances().cancel();
    }

    @Override
    public void onActivityStopped(@NotNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NotNull Activity activity) {
     //   if (serviceConnection!=null)
      //  unbindService(serviceConnection);
    }






}
