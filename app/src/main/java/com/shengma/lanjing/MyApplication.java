package com.shengma.lanjing;

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

import androidx.annotation.NonNull;

import com.liulishuo.filedownloader.FileDownloader;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.LiwuPathBean;
import com.shengma.lanjing.beans.MyObjectBox;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.beans.YongHuListBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.dialogall.CommonData;
import com.shengma.lanjing.dialogall.CommonDialogService;
import com.shengma.lanjing.dialogall.ToastUtils;
import com.shengma.lanjing.liveroom.MLVBLiveRoomImpl;
import com.shengma.lanjing.utils.TCUserMgr;
import com.tencent.bugly.Bugly;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.rtmp.TXLiveBase;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import okhttp3.OkHttpClient;

import static com.shengma.lanjing.utils.Consts.APP_ID;


/**
 * Created by Administrator on 2018/8/3.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private Box<BaoCunBean> baoCunBeanBox=null;
    private Box<XiaZaiLiWuBean> xiaZaiLiWuBeanBox=null;
    private Box<LiwuPathBean> liwuPathBeanBox=null;
    private Box<YongHuListBean> yongHuListBeanBox=null;
    public static MyApplication myApplication;

    public OkHttpClient okHttpClient =null;
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"lanjing";
    public static final String SDPATH2 = Environment.getExternalStorageDirectory().getAbsolutePath()+
            File.separator+"ruitongyqt";

    private static final String LICENCE_URL = "https://license.vod2.myqcloud.com/license/v1/b85e214501a5b319de43c95ca3502b76/TXLiveSDK.licence";
    private static final String LICENCE_KEY = "1d72ec05313e46055af4add54211508a";

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


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

        // 必须：初始化 LiteAVSDK Licence。 用于直播推流鉴权。
        TXLiveBase.getInstance().setLicence(this, LICENCE_URL, LICENCE_KEY);
        // 必须：初始化 MLVB 组件
        MLVBLiveRoomImpl.sharedInstance(this);
        // 必须：初始化全局的 用户信息管理类，记录个人信息。
        TCUserMgr.getInstance().initContext(getApplicationContext());

        FileDownloader.setup(this);//下载初始化

        baoCunBeanBox= mBoxStore.boxFor(BaoCunBean.class);
        xiaZaiLiWuBeanBox= mBoxStore.boxFor(XiaZaiLiWuBean.class);
        liwuPathBeanBox= mBoxStore.boxFor(LiwuPathBean.class);
        yongHuListBeanBox= mBoxStore.boxFor(YongHuListBean.class);

        BaoCunBean  baoCunBean = mBoxStore.boxFor(BaoCunBean.class).get(123456L);
        if (baoCunBean == null) {
            baoCunBean = new BaoCunBean();
            baoCunBean.setMeiyan(5);
            baoCunBean.setMeibai(5);
            baoCunBean.setHongrun(5);
            baoCunBean.setId(123456L);
            mBoxStore.boxFor(BaoCunBean.class).put(baoCunBean);
        }

        okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(18000, TimeUnit.MILLISECONDS)
                .connectTimeout(18000, TimeUnit.MILLISECONDS)
                .readTimeout(18000, TimeUnit.MILLISECONDS)
              //  .cookieJar(new CookiesManager())
                //        .retryOnConnectionFailure(true)
                .build();

        regToWx();


    }

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
    public Box<BaoCunBean> getBaoCunBeanBox(){
        return baoCunBeanBox;
    }
    public Box<XiaZaiLiWuBean> getXiaZaiLiWuBeanBox(){
        return xiaZaiLiWuBeanBox;
    }
    public Box<LiwuPathBean> getLiwuPathBeanBox(){
        return liwuPathBeanBox;
    }
    public Box<YongHuListBean> getYongHuListBeanBox(){
        return yongHuListBeanBox;
    }
    public BaoCunBean getBaoCunBean(){
        return baoCunBeanBox.get(123456);
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
