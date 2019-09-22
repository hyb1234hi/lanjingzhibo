package com.shengma.lanjing.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.KaiBoBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.dialogs.FenXiangDialog;
import com.shengma.lanjing.dialogs.JuLiDialog;
import com.shengma.lanjing.dialogs.PhotoDialog;
import com.shengma.lanjing.dialogs.PingDaoDialog;
import com.shengma.lanjing.liveroom.IMLVBLiveRoomListener;
import com.shengma.lanjing.liveroom.MLVBLiveRoom;
import com.shengma.lanjing.liveroom.roomutil.commondef.LoginInfo;
import com.shengma.lanjing.ui.zhibo.ZhiBoActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class KaiBoActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.fengmian)
    ImageView fengmian;
    @BindView(R.id.weizhi)
    View weizhi;
    @BindView(R.id.pingdao)
    View pingdao;
    @BindView(R.id.kaibo)
    TextView kaibo;
    @BindView(R.id.fenxiang)
    ImageView fenxiang;
    @BindView(R.id.zhuti)
    EditText zhuti;
    @BindView(R.id.frrrd)
    TextView frrrd;
    private PhotoDialog photoDialog;
    ZLoadingDialog dialog;
    private int liveType = -1;
    private String fengmianPath;
    private LocationClient locationClient;
    private int count=0;
    private String jd="",wd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_bo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        initLocationOption();
    }

    @OnClick({R.id.fanhui, R.id.fengmian, R.id.weizhi, R.id.pingdao, R.id.kaibo, R.id.fenxiang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.fengmian: {

                photoDialog = new PhotoDialog();
                photoDialog.show(getSupportFragmentManager(), "photodialog");

                break;
            }
            case R.id.weizhi: {
                JuLiDialog dialog = new JuLiDialog();
                dialog.show(getSupportFragmentManager(), "juli");

                break;
            }
            case R.id.pingdao: {
                PingDaoDialog daoDialog = new PingDaoDialog();
                daoDialog.show(getSupportFragmentManager(), "pingdao");

                break;
            }
            case R.id.kaibo:
                if (fengmianPath==null || fengmianPath.equals("")){
                    ToastUtils.showInfo(KaiBoActivity.this,"请先上传封面");
                    return;
                }
                if (zhuti.getText().toString().trim().equals("")){
                    ToastUtils.showInfo(KaiBoActivity.this,"请先填写主题");
                    return;
                }
                if (liveType==-1){
                    ToastUtils.showInfo(KaiBoActivity.this,"请先选择直播类型");
                    return;
                }

                dialog = new ZLoadingDialog(KaiBoActivity.this);
                dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                        .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                        .setHintText("加载中...")
                        .setHintTextSize(16) // 设置字体大小 dp
                        .setHintTextColor(Color.WHITE)  // 设置字体颜色
                        .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                        .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                        .show();
                link_end();
                break;
            case R.id.fenxiang: {
                FenXiangDialog dialog = new FenXiangDialog();
                dialog.show(getSupportFragmentManager(), "fenxiang");

                break;
            }
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1003) {
            dialog = new ZLoadingDialog(KaiBoActivity.this);
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("加载中...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();

            link_loging(msgWarp.getMsg());
            Log.d("KaiBoActivity", msgWarp.getMsg());
        } else if (msgWarp.getType()==100){
            liveType = Integer.parseInt(msgWarp.getTemp());
            frrrd.setText(msgWarp.getMsg());
        }else if (msgWarp.getType()==6668){
                if (msgWarp.getMsg().equals("kai")){
                    initLocationOption();
                }else {
                    jd="";
                    wd="";
                }
        }
    }

    @Override
    protected void onRestart() {
//        if (photoDialog != null)
//            photoDialog.dismiss();

        Log.d("KaiBoActivity", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRIVATE_CODE) {
            Log.d("MainActivity", "resultCode:" + resultCode);
            if (resultCode!=0){
                ToastUtils.showInfo(KaiBoActivity.this,"打开GPS失败");
            }else {
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
            jd=longitude+"";
            wd=latitude+"";
        } else {
            ToastUtils.showInfo(KaiBoActivity.this,"无法获取到位置信息");
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
                    getLocation();//getLocation为定位方法
                }
            } else {
                getLocation();//getLocation为定位方法
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
                getLocation();
            } else {
                showGPSContacts();
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


    private void link_loging(String path) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/user/upload/img");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(KaiBoActivity.this, "获取数据失败,请检查网络");
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                if (dialog != null)
                    dialog.dismiss();
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传图片:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    fengmianPath=jsonObject.get("result").getAsString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fengmian.setImageBitmap(BitmapFactory.decodeFile(path));
                            if (photoDialog != null)
                                photoDialog.dismiss();

                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(KaiBoActivity.this, "获取数据失败");
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        });
    }
//    {
//        "coverImg": "string",
//            "latitude": 0,
//            "longitude": 0,
//            "title": "string",
//            "type": 0
//    }

    private void link_kaibo() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object=new JSONObject();
        try {
            object.put("coverImg",fengmianPath);
            object.put("latitude",wd);
            object.put("longitude",jd);
            object.put("title",zhuti.getText().toString().trim());
            object.put("type",liveType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            object.put("coverImg","h");
//            object.put("latitude",0);
//            object.put("longitude",0);
//            object.put("title","测试");
//            object.put("type","1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Log.d("KaiBoActivity", "liveType:" + object.toString());
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/start");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(KaiBoActivity.this, "获取数据失败,请检查网络");
                if (dialog != null)
                    dialog.dismiss();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "开播:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    KaiBoBean bean = gson.fromJson(jsonObject, KaiBoBean.class);
                    BaoCunBean baoCunBean= MyApplication.myApplication.getBaoCunBeanBox().get(123456);
                  if (baoCunBean!=null){
                      baoCunBean.setRoomId(bean.getResult().getRoomId());
                      baoCunBean.setPushUrl(bean.getResult().getPushUrl());
                      baoCunBean.setPlayUrl(bean.getResult().getPlayUrl());
                      MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                      MLVBLiveRoom.sharedInstance(MyApplication.myApplication).setmHasAddAnchor(true,baoCunBean.getUserid()+"",1);
                      MLVBLiveRoom.sharedInstance(MyApplication.myApplication).exitRoom(new IMLVBLiveRoomListener.ExitRoomCallback() {
                          @Override
                          public void onError(int errCode, String errInfo) {
                              if (dialog != null)
                                  dialog.dismiss();
                              LoginInfo loginInfo=new LoginInfo(Integer.parseInt(baoCunBean.getSdkAppId()),baoCunBean.getUserid()+"",baoCunBean.getNickname(),baoCunBean.getHeadImage(),baoCunBean.getImUserSig());
                              MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
                                  @Override
                                  public void onError(int errCode, String errInfo) {
                                      ToastUtils.showInfo(KaiBoActivity.this,"IM登录失败");
                                  }
                                  @Override
                                  public void onSuccess() {
                                      startActivity(new Intent(KaiBoActivity.this, ZhiBoActivity.class));
                                  }
                              });
                          }
                          @Override
                          public void onSuccess() {
                              if (dialog != null)
                                  dialog.dismiss();
                              LoginInfo loginInfo=new LoginInfo(Integer.parseInt(baoCunBean.getSdkAppId()),baoCunBean.getUserid()+"",baoCunBean.getNickname(),baoCunBean.getHeadImage(),baoCunBean.getImUserSig());
                              MLVBLiveRoom.sharedInstance(MyApplication.myApplication).login(loginInfo, new IMLVBLiveRoomListener.LoginCallback() {
                                  @Override
                                  public void onError(int errCode, String errInfo) {
                                      ToastUtils.showInfo(KaiBoActivity.this,"IM登录失败");
                                  }
                                  @Override
                                  public void onSuccess() {
                                      startActivity(new Intent(KaiBoActivity.this, ZhiBoActivity.class));
                                  }
                              });
                          }
                      });
                  }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(KaiBoActivity.this, "获取数据失败");
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        });
    }

    private void link_end() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object=new JSONObject();
        try {
            object.put("","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/live/end");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(KaiBoActivity.this, "获取数据失败,请检查网络");
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "结束直播:" + ss);
                    link_kaibo();
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(KaiBoActivity.this, "获取数据失败");
                    if (dialog != null)
                        dialog.dismiss();
                }
            }
        });
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
                jd=longitude+"";
                wd=latitude+"";
                locationClient.stop();
            }else {
                count++;
                locationClient.restart();
                if (count>=6){
                    locationClient.stop();
                    ToastUtils.showInfo(KaiBoActivity.this,"定位失败");
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


