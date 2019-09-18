package com.shengma.lanjing.ui;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.os.Bundle;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.LiwuPathBean;
import com.shengma.lanjing.beans.LiwuPathBean_;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.roomutil.commondef.LoginInfo;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.ControlScrollViewPager;
import com.shengma.lanjing.views.MyFragmentPagerAdapter;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.query.LazyList;
import io.objectbox.query.Query;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;




public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.tv4)
    TextView tv4;
    private LinkedBlockingQueue<XiaZaiLiWuBean> linkedBlockingQueue=new LinkedBlockingQueue<>(200);

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();
    private TanChuangThread tanChuangThread;
    private ControlScrollViewPager viewpage;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        link_userinfo();
        viewpage = findViewById(R.id.viewpage);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpage.setAdapter(myFragmentPagerAdapter);
        //设置当前页的ID
        viewpage.setCurrentItem(0);
        //添加翻页监听事件
        viewpage.addOnPageChangeListener(this);
        viewpage.setOffscreenPageLimit(4);


        tanChuangThread = new TanChuangThread();
        tanChuangThread.start();
        link_xiazai();

      //  Log.d("MainActivity", "MyA:" + MyApplication.myApplication.getLiwuPathBeanBox().getAll().size());



    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp){

        if (msgWarp.getType()==1002){
            finish();
        }
    }


    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                resetSelected();
                tv1.setTextColor(Color.parseColor("#3EE1F7"));
                im1.setBackgroundResource(R.drawable.shouye1);
                viewpage.setCurrentItem(PAGE_ONE);
                break;
            case R.id.ll2:
                resetSelected();
                tv2.setTextColor(Color.parseColor("#F36D87"));
                im2.setBackgroundResource(R.drawable.fujin1);
                viewpage.setCurrentItem(PAGE_TWO);
                break;
            case R.id.ll3:
                resetSelected();
                tv3.setTextColor(Color.parseColor("#FB9210"));
                im3.setBackgroundResource(R.drawable.xiaoxi1);
                viewpage.setCurrentItem(PAGE_THREE);
                break;
            case R.id.ll4:
                resetSelected();
                tv4.setTextColor(Color.parseColor("#9327F5"));
                im4.setBackgroundResource(R.drawable.wode1);
                viewpage.setCurrentItem(PAGE_FOUR);
                break;
        }
    }

    private void link_userinfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/user/info");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(MainActivity.this,"获取数据失败,请检查网络");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonObject jsonObject = null;
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "用户信息"+ss);
                    jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    UserInfoBean userInfoBean = gson.fromJson(jsonObject, UserInfoBean.class);
                    Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
                    BaoCunBean bean = baoCunBeanBox.get(123456);
                    bean.setAnchorLevel(userInfoBean.getResult().getAnchorLevel());
                    bean.setAuth(userInfoBean.getResult().getAuth());
                    bean.setDuration(userInfoBean.getResult().getDuration());
                    bean.setFans(userInfoBean.getResult().getFans());
                    bean.setHeadImage(userInfoBean.getResult().getHeadImage());
                    bean.setIdNumber(userInfoBean.getResult().getIdNumber());
                    bean.setIdols(userInfoBean.getResult().getIdols());
                    bean.setNickname(userInfoBean.getResult().getNickname());
                    bean.setRealName(userInfoBean.getResult().getRealName());
                    bean.setSex(userInfoBean.getResult().getSex());
                    bean.setUserCode(userInfoBean.getResult().getUserCode());
                    bean.setUserid(userInfoBean.getResult().getId());
                    bean.setUserLevel(userInfoBean.getResult().getUserLevel());
                    baoCunBeanBox.put(bean);

                    LoginInfo loginInfo=new LoginInfo(Integer.parseInt(bean.getSdkAppId()),bean.getUserid()+"",bean.getNickname(),bean.getHeadImage(),bean.getImUserSig());
                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setCameraMuteImage(BitmapFactory.decodeResource(getResources(), R.drawable.pause_publish));
                    //登录IM
                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
                        @Override
                        public void onError(int errCode, String errInfo) {
                            Log.d("ZhiBoActivity", "errCode:" + errCode);
                            Log.d("ZhiBoActivity", errInfo);
                        }
                        @Override
                        public void onSuccess() {
                            Log.d("ZhiBoActivity", "IM登录成功");


                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(MainActivity.this,"获取数据失败");
                    try {
                        if (jsonObject!=null)
                       if (jsonObject.get("code").getAsInt()==4401){
                           MyApplication.myApplication.getBaoCunBeanBox().removeAll();
                           BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBeanBox().get(123456L);
                           if (baoCunBean == null) {
                               baoCunBean = new BaoCunBean();
                               baoCunBean.setId(123456L);
                               baoCunBean.setMeiyan(5);
                               baoCunBean.setMeibai(5);
                               baoCunBean.setHongrun(5);
                               MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                           }
                           Intent intent=new Intent(MainActivity.this,LogingActivity.class);
                           intent.putExtra("aaa","aaa");
                           startActivity(intent);
                           finish();
                       }
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    private class TanChuangThread extends Thread {
        boolean isRing;
        boolean isX=true;
        int A=1;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    if (isX) {
                        isX=false;
                        XiaZaiLiWuBean subject = linkedBlockingQueue.poll();
                        if (subject==null){
                            A+=1;
                            if (A==10){

                                Log.d("MainActivity", "结束大循环");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        jieya();
                                    }
                                }).start();
                                isRing=true;
                            }
                            SystemClock.sleep(2000);
                            isX=true;
                        }else {
                            if (!subject.getSpecialUrl().equals("")){
                                FileDownloader.getImpl().create(subject.getSpecialUrl())
                                        .setPath(MyApplication.SDPATH+File.separator+subject.getId()+".zip")
                                        .setListener(new FileDownloadListener() {
                                            @Override
                                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            }
                                            @Override
                                            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                                            }
                                            @Override
                                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                            }

                                            @Override
                                            protected void blockComplete(BaseDownloadTask task) {
                                            }

                                            @Override
                                            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                                                isX=true;
                                            }
                                            @Override
                                            protected void completed(BaseDownloadTask task) {
                                                //完成
                                                isX=true;
                                                subject.setD(true);
                                                MyApplication.myApplication.getXiaZaiLiWuBeanBox().put(subject);
                                            }
                                            @Override
                                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                isX=true;
                                            }
                                            @Override
                                            protected void error(BaseDownloadTask task, Throwable e) {
                                                isX=true;
                                            }
                                            @Override
                                            protected void warn(BaseDownloadTask task) {
                                                isX=true;
                                            }
                                        }).start();
                            }else {//下一个
                                isX=true;
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void interrupt() {
            isRing = true;
            super.interrupt();
        }
    }

    private void jieya(){
        List<XiaZaiLiWuBean> xiaZaiLiWuBeanList=MyApplication.myApplication.getXiaZaiLiWuBeanBox().getAll();
        for (XiaZaiLiWuBean xiaZaiLiWuBean:xiaZaiLiWuBeanList){
            if (!xiaZaiLiWuBean.isJY() && xiaZaiLiWuBean.isD()){//没有解压
                ZipFile zipFile=null;
                List fileHeaderList=null;
                try {
                    // Initiate ZipFile object with the path/name of the zip file.
                  //  Log.d("MainActivity", MyApplication.SDPATH + File.separator + xiaZaiLiWuBean.getId() + ".zip");
                    zipFile = new ZipFile(MyApplication.SDPATH+File.separator+xiaZaiLiWuBean.getId()+".zip");
                    zipFile.setFileNameCharset("GBK");
                    fileHeaderList = zipFile.getFileHeaders();
                    // Loop through the file headers
                    Log.d("MainActivity", "fileHeaderList.size():" + fileHeaderList.size());
                    //创建解压文件目录
                    String path222=MyApplication.SDPATH+File.separator+xiaZaiLiWuBean.getId();
                    File file = new File(path222);
                    if (!file.exists()) {
                        Log.d("ggg", "file.mkdirs():" + file.mkdirs());
                    }

                    List<LiwuPathBean> lll = new ArrayList<>();
                    for (int i = 0; i < fileHeaderList.size(); i++) {
                        FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
                        //	FileHeader fileHeader2 = (FileHeader) fileHeaderList.get(0);
                        //Log.d(TAG, fileHeader2.getFileName());
                      //  zipFile.extractFile(fileHeader.getFileName(), path222);
                        //重命名
                        fileHeader.setFileName(fileHeader.getFileName().replaceAll("img_",""));
                        LiwuPathBean pathBean=new LiwuPathBean();
                        pathBean.setId(System.currentTimeMillis());
                        pathBean.setPath(path222+File.separator+fileHeader.getFileName());
                      // Log.d("MainActivity", fileHeader.getFileName().replaceAll(".png",""));
                        int ss = Integer.parseInt(fileHeader.getFileName().replaceAll(".png",""));
                        pathBean.setUid(ss);
                        pathBean.setSid(xiaZaiLiWuBean.getId());
                        lll.add(pathBean);
                    }

                    Collections.sort(lll);
                    for (LiwuPathBean liwuPathBean:lll){
                        MyApplication.myApplication.getLiwuPathBeanBox().put(liwuPathBean);
                    }
                    lll.clear();
                    //重命名之后解压
                    zipFile.setRunInThread(true); // true 在子线程中进行解压 false主线程中解压
                    zipFile.extractAll(path222); // 将压缩文件解压到filePath中..
                    xiaZaiLiWuBean.setJY(true);
                    MyApplication.myApplication.getXiaZaiLiWuBeanBox().put(xiaZaiLiWuBean);

                } catch (final ZipException e) {
                    e.printStackTrace();
                    Log.d("MainActivity", e.getMessage()+"解压异常");
                }
            }
        }
    }


    private void link_xiazai() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/gift/load?page=1&pageSize=200");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(MainActivity.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "下载礼物:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    JsonArray jsonArray=jsonObject.get("result").getAsJsonArray();
                    int size=jsonArray.size();
                    Log.d("MainActivity", "size:" + size);
                    for (int i=0;i<size;i++){
                       JsonObject object=jsonArray.get(i).getAsJsonObject();
                      XiaZaiLiWuBean xiaZaiLiWuBean=MyApplication.myApplication.getXiaZaiLiWuBeanBox().get(object.get("id").getAsInt());
                      if (xiaZaiLiWuBean==null){
                          Log.d("MainActivity", "新礼物");
                          XiaZaiLiWuBean bean=new XiaZaiLiWuBean();
                          bean.setD(false);
                          bean.setGiftMoney(object.get("giftMoney").getAsString());
                          bean.setGiftName(object.get("giftName").getAsString());
                          bean.setGiftUrl(object.get("giftUrl").getAsString());
                          bean.setSpecialUrl(object.get("specialUrl").getAsString());
                          bean.setId(object.get("id").getAsInt());
                          bean.setType(object.get("type").getAsInt());
                          MyApplication.myApplication.getXiaZaiLiWuBeanBox().put(bean);
                          linkedBlockingQueue.offer(bean);
                      }else {
                          Log.d("MainActivity", "已经存在礼物");
                          if (!xiaZaiLiWuBean.isD()){//没有下载
                              Log.d("MainActivity", "没有下载的礼物");
                              linkedBlockingQueue.offer(xiaZaiLiWuBean);
                          }else {
                              Log.d("MainActivity", "已经下载的礼物");
                          }
                      }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(MainActivity.this,"获取数据失败");
                }
            }
        });
    }

    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewpage.getCurrentItem()) {
                case PAGE_ONE: {
                    resetSelected();
                    tv1.setTextColor(Color.parseColor("#3EE1F7"));
                    im1.setBackgroundResource(R.drawable.shouye1);
                    break;
                }
                case PAGE_TWO: {
                    resetSelected();
                    tv2.setTextColor(Color.parseColor("#F36D87"));
                    im2.setBackgroundResource(R.drawable.fujin1);
                    break;
                }
                case PAGE_THREE: {
                    resetSelected();
                    tv3.setTextColor(Color.parseColor("#FB9210"));
                    im3.setBackgroundResource(R.drawable.xiaoxi1);
                    break;
                }
                case PAGE_FOUR: {
                    resetSelected();
                    tv4.setTextColor(Color.parseColor("#9327F5"));
                    im4.setBackgroundResource(R.drawable.wode1);
                    break;
                }
            }
        }
    }

    private void resetSelected(){
        tv1.setTextColor(Color.parseColor("#999999"));
        tv2.setTextColor(Color.parseColor("#999999"));
        tv3.setTextColor(Color.parseColor("#999999"));
        tv4.setTextColor(Color.parseColor("#999999"));
        im1.setBackgroundResource(R.drawable.shouye2);
        im2.setBackgroundResource(R.drawable.fujin2);
        im3.setBackgroundResource(R.drawable.xiaoxi2);
        im4.setBackgroundResource(R.drawable.wode2);
    }

    @Override
    protected void onDestroy() {

        if (tanChuangThread != null) {
            tanChuangThread.isRing = true;
            tanChuangThread.interrupt();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
