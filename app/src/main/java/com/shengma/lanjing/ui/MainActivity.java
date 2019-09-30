package com.shengma.lanjing.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.beans.XiaZaiLiWuBean;
import com.shengma.lanjing.utils.AES;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.utils.Util;
import com.shengma.lanjing.utils.Utils;
import com.shengma.lanjing.views.ControlScrollViewPager;
import com.shengma.lanjing.views.MyFragmentPagerAdapter;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


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

    private TanChuangThread tanChuangThread;
    private ControlScrollViewPager viewpage;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    private String jd="",wd="";
    private LocationClient locationClient;
    private int count=0;


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
//        for (int i=0;i<1000;i++){
//            YongHuListBean bean=new YongHuListBean();
//            bean.setId(System.currentTimeMillis());
//            bean.setName("恻然");
//            bean.setHeadImage("http://pic13.nipic.com/20110409/7119492_114440620000_2.jpg");
//            Random random=new Random();
//            bean.setJingbi(random.nextInt(1000000));
//            MyApplication.myApplication.getYongHuListBeanBox().put(bean);
//        }≤≥lololoõ8i9

        //(A-B)÷Bx100%
     //  float ss= (float) ((1-100000)/100000.0);
      // Log.d("MainActivity", "ss:" + ss);
//        try {
//            String ss=Utils.desEncrypt(MyApplication.myApplication.getBaoCunBean().getUserid()+"","lanjing_");
//            Log.d("MainActivity", ss);
//            String yy = Utils.desDecrypt(ss.getBytes(),"lanjing_");
//            Log.d("MainActivity", yy);
//        } catch (Exception e) {
//           Log.d("MainActivity", e.getMessage());
//
//        }

        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        Log.d("MainActivity", rid);

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
                count=0;
                showGPSContacts();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRIVATE_CODE) {
           Log.d("MainActivity", "resultCode:" + resultCode);
            if (resultCode!=0){
                ToastUtils.showInfo(MainActivity.this,"打开GPS失败");
            }else {
               // initLocationOption();
                showGPSContacts();
            }
        }
    }

    /**
     * 获取到当前位置的经纬度
     * @param location
     */
    private void updateLocation(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Log.e("MainActivity","维度：" + latitude + "\n经度" + longitude);
            EventBus.getDefault().post(new MsgWarp(6666,latitude+"",longitude+""));
        } else {
            ToastUtils.showInfo(MainActivity.this,"无法获取到位置信息");
            Log.e("MainActivity","无法获取到位置信息");
        }
    }



    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    /**
     * 检测GPS、位置权限是否开启
     */
    public void showGPSContacts() {
        LocationManager lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS,
                            BAIDU_READ_PHONE_STATE);
                } else {
                    initLocationOption();
                }
            } else {
                initLocationOption();
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // requestCode即所声明的权限获取码，在checkSelfPermission时传入
        if (requestCode == BAIDU_READ_PHONE_STATE) {//如果用户取消，permissions可能为null.
            if (grantResults[0] == PERMISSION_GRANTED) {  //有权限
                // 获取到权限，作相应处理
                initLocationOption();
            } else {
                Toast.makeText(this, "获取定位权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取具体位置的经纬度
     */
    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        /**这段代码不需要深究，是locationManager.getLastKnownLocation(provider)自动生成的，不加会出错**/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (provider!=null){
            Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
            updateLocation(location);
        }
    }


    private void link_userinfo() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/user/info");
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

//                    LoginInfo loginInfo=new LoginInfo(Integer.parseInt(bean.getSdkAppId()),bean.getUserid()+"",bean.getNickname(),bean.getHeadImage(),bean.getImUserSig());
//                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setCameraMuteImage(BitmapFactory.decodeResource(getResources(), R.drawable.pause_publish));
//                    //登录IM
//                    MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
//                        @Override
//                        public void onError(int errCode, String errInfo) {
//                            Log.d("ZhiBoActivity", "errCode:" + errCode);
//                            Log.d("ZhiBoActivity", errInfo);
//                        }
//                        @Override
//                        public void onSuccess() {
//                            Log.d("ZhiBoActivity", "IM登录成功");
//                        }
//                    });
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
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
                           ToastUtils.showInfo(MainActivity.this,"登录已过期,请重新登录");
                           startActivity(intent);
                           finish();
                       }else {
                            ToastUtils.showError(MainActivity.this,e.getMessage()+"");
                        }
                    }catch (Exception e1){
                        e1.printStackTrace();
                        ToastUtils.showError(MainActivity.this,e1.getMessage()+"");
                    }

                }
            }
        });
    }

    private boolean isX=true;
    private class TanChuangThread extends Thread {
        boolean isRing;
        int A=1;

        @Override
        public void run() {
            while (!isRing) {
                try {
                    //有动画 ，延迟到一秒一次
                    if (isX) {
                        isX=false;
                        XiaZaiLiWuBean subject = linkedBlockingQueue.poll();
                        if (subject==null){//空是取完了
                            A+=1;
                            if (A==10){
//                                Log.d("MainActivity", "结束大循环");
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        jieya();
//                                    }
//                                }).start();
                                isRing=true;
                            }
                            SystemClock.sleep(1000);
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
                                               // isX=true;
                                            }
                                            @Override
                                            protected void completed(BaseDownloadTask task) {
                                                //完成
                                               // isX=true;
                                                subject.setD(true);
                                                MyApplication.myApplication.getXiaZaiLiWuBeanBox().put(subject);
                                                Log.d("MainActivity", "下载完成:"+task.getUrl());
                                                jieya(subject);
                                            }
                                            @Override
                                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                                //isX=true;
                                            }
                                            @Override
                                            protected void error(BaseDownloadTask task, Throwable e) {
                                                isX=true;
                                            }
                                            @Override
                                            protected void warn(BaseDownloadTask task) {
                                               // isX=true;
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

    private void jieya(XiaZaiLiWuBean xiaZaiLiWuBean){
      //  boolean isA=true;
     //   List<XiaZaiLiWuBean> xiaZaiLiWuBeanList=MyApplication.myApplication.getXiaZaiLiWuBeanBox().getAll();
      //  for (XiaZaiLiWuBean xiaZaiLiWuBean:xiaZaiLiWuBeanList){
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
                        Log.d("MainActivity", "file.mkdirs():" + file.mkdirs());
                    }
//                    List<LiwuPathBean> lll = new ArrayList<>();
//                    for (int i = 0; i < fileHeaderList.size(); i++) {
//                        FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
//                        //	FileHeader fileHeader2 = (FileHeader) fileHeaderList.get(0);
//                        //Log.d(TAG, fileHeader2.getFileName());
//                      //  zipFile.extractFile(fileHeader.getFileName(), path222);
//                        //重命名
//                        fileHeader.setFileName(fileHeader.getFileName().replaceAll("img_",""));
//                        LiwuPathBean pathBean=new LiwuPathBean();
//                        pathBean.setId(System.currentTimeMillis());
//                        pathBean.setPath(path222+File.separator+fileHeader.getFileName());
//                      // Log.d("MainActivity", fileHeader.getFileName().replaceAll(".png",""));
//                        int ss = Integer.parseInt(fileHeader.getFileName().replaceAll(".png",""));
//                        pathBean.setUid(ss);
//                        pathBean.setSid(xiaZaiLiWuBean.getId());
//                        lll.add(pathBean);
//                    }
//
//                    Collections.sort(lll);
//                    for (LiwuPathBean liwuPathBean:lll){
//                        MyApplication.myApplication.getLiwuPathBeanBox().put(liwuPathBean);
//                    }
//                    lll.clear();
                    //重命名之后解压
                    zipFile.setRunInThread(false); // true 在子线程中进行解压 false主线程中解压
                    zipFile.extractAll(path222); // 将压缩文件解压到filePath中..
                    xiaZaiLiWuBean.setJY(true);
                    MyApplication.myApplication.getXiaZaiLiWuBeanBox().put(xiaZaiLiWuBean);
                    isX=true;
                } catch (final ZipException e) {
                    e.printStackTrace();
                   isX=true;
                    Log.d("MainActivity", e.getMessage()+"解压异常");
                }
            }else {
                Log.d("MainActivity", "已经解压过");
                isX=true;
            }
   //     }
     //   BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();
//        if (isA){
//            baoCunBean.setLiwuISOK(true);
//            MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
//        }
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
                          if (!xiaZaiLiWuBean.isD() || !xiaZaiLiWuBean.isJY()){//没有下载
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

    /**
     * 初始化定位参数配置
     */


    private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
         locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        //注册监听函数
        locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
       // locationOption.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
       // locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
       // locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
        //开始定位
        locationClient.start();

    }


    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            int errorCode = location.getLocType();
             Log.d("MyLocationListener", "errorCode:" + errorCode);
             Log.d("MyLocationListener", "latitude:" + latitude);
             Log.d("MyLocationListener", "longitude:" + longitude);

            if (errorCode==161 || errorCode==61){
                EventBus.getDefault().post(new MsgWarp(6666,longitude+"",latitude+""));
                locationClient.stop();
            }else {
                count++;
                locationClient.restart();
                if (count>=3){
                    locationClient.stop();
                    ToastUtils.showInfo(MainActivity.this,"定位失败");
                }
                return;
            }
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }
}
