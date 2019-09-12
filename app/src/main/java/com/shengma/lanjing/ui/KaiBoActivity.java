package com.shengma.lanjing.ui;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_bo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


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
//                if (fengmianPath==null || fengmianPath.equals("")){
//                    ToastUtils.showInfo(KaiBoActivity.this,"请先上传封面");
//                    return;
//                }
//                if (zhuti.getText().toString().trim().equals("")){
//                    ToastUtils.showInfo(KaiBoActivity.this,"请先填写主题");
//                    return;
//                }
//                if (liveType==-1){
//                    ToastUtils.showInfo(KaiBoActivity.this,"请先选择直播类型");
//                    return;
//                }

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
        } else {
            liveType = msgWarp.getType();
            frrrd.setText(msgWarp.getMsg());
        }

    }

    @Override
    protected void onRestart() {
        if (photoDialog != null)
            photoDialog.dismiss();

        Log.d("KaiBoActivity", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
//        try {
//            object.put("coverImg",fengmianPath);
//            object.put("latitude",0);
//            object.put("longitude",0);
//            object.put("title",zhuti.getText().toString().trim());
//            object.put("type",liveType);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            object.put("coverImg","h");
            object.put("latitude",0);
            object.put("longitude",0);
            object.put("title",zhuti.getText().toString().trim());
            object.put("type","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
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
                              startActivity(new Intent(KaiBoActivity.this, ZhiBoActivity.class));
                          }

                          @Override
                          public void onSuccess() {
                              if (dialog != null)
                                  dialog.dismiss();
                              startActivity(new Intent(KaiBoActivity.this, ZhiBoActivity.class));
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
}


