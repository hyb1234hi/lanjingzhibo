package com.example.myapplication2;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;

import android.util.Log;
import android.view.WindowManager;

import com.example.myapplication2.beans.BaoCunBean;
import com.example.myapplication2.beans.MyObjectBox;
import com.example.myapplication2.dialogall.CommonData;
import com.example.myapplication2.dialogall.CommonDialogService;
import com.example.myapplication2.dialogall.ToastUtils;

import com.tencent.bugly.Bugly;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.jetbrains.annotations.NotNull;

import java.io.File;

import io.objectbox.Box;
import io.objectbox.BoxStore;


/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private Box<BaoCunBean> baoCunBeanBox=null;
    public static MyApplication myApplication;

    private static final String APP_ID = "wxa118e3198ef780bd";
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"ruitongzipyqt";
    public static final String SDPATH2 = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"ruitongyqt";



    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        BoxStore mBoxStore = MyObjectBox.builder().androidContext(this).build();

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

        baoCunBeanBox= mBoxStore.boxFor(BaoCunBean.class);


        BaoCunBean  baoCunBean = mBoxStore.boxFor(BaoCunBean.class).get(123456L);
        if (baoCunBean == null) {
            baoCunBean = new BaoCunBean();
            baoCunBean.setId(123456L);
            mBoxStore.boxFor(BaoCunBean.class).put(baoCunBean);
        }

        regToWx();
    }


    public Box<BaoCunBean> getBaoCunBeanBox(){
        return baoCunBeanBox;
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



    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("MyApplication", "intent:" + intent);
                // 将该app注册到微信
                api.registerApp(APP_ID);

            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

}
