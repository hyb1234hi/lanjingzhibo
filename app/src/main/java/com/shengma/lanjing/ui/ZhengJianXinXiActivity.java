package com.shengma.lanjing.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.dialogs.PhotoDialog;
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


public class ZhengJianXinXiActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.haoma)
    EditText haoma;
    @BindView(R.id.shangchuan1)
    View shangchuan1;
    @BindView(R.id.shangchuan2)
    View shangchuan2;
    @BindView(R.id.wancheng)
    Button wancheng;
    @BindView(R.id.f1)
    ImageView f1;
    @BindView(R.id.kkk1)
    TextView kkk1;
    @BindView(R.id.f2)
    ImageView f2;
    @BindView(R.id.kkk2)
    TextView kkk2;
    private ZLoadingDialog dialog;
    private PhotoDialog photoDialog;
    private boolean isAB=true;
    private String fengmianPath1,fengmianPath2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zheng_jian_xin_xi);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1003) {
            dialog = new ZLoadingDialog(ZhengJianXinXiActivity.this);
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
        }
    }

    @OnClick({R.id.fanhui, R.id.shangchuan1, R.id.shangchuan2, R.id.wancheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.shangchuan1:
                isAB=true;
                photoDialog = new PhotoDialog();
                photoDialog.show(getSupportFragmentManager(), "photodialog");
                break;
            case R.id.shangchuan2:
                isAB=false;
                photoDialog = new PhotoDialog();
                photoDialog.show(getSupportFragmentManager(), "photodialog");
                break;
            case R.id.wancheng:
                String n =name.getText().toString().trim();
                String h=haoma.getText().toString().trim();
                if (n.equals("")){
                    ToastUtils.showInfo(ZhengJianXinXiActivity.this,"姓名为空");
                    return;
                }
                if (h.equals("")){
                    ToastUtils.showInfo(ZhengJianXinXiActivity.this,"身份证号码为空");
                    return;
                }
                if (fengmianPath1==null|| fengmianPath1.equals("")){
                    ToastUtils.showInfo(ZhengJianXinXiActivity.this,"证件照片为空");
                    return;
                }
                if (fengmianPath2==null|| fengmianPath2.equals("")){
                    ToastUtils.showInfo(ZhengJianXinXiActivity.this,"证件照片为空");
                    return;
                }
                dialog = new ZLoadingDialog(ZhengJianXinXiActivity.this);
                dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                        .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                        .setHintText("加载中...")
                        .setHintTextSize(16) // 设置字体大小 dp
                        .setHintTextColor(Color.WHITE)  // 设置字体颜色
                        .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                        .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                        .show();
                link_loging(n,h);
                break;
        }
    }




    private void link_loging(String path) {
       // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
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
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/user/upload/img");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ZhengJianXinXiActivity.this, "获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                        if (photoDialog != null)
                            photoDialog.dismiss();
                    }
                });

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传图片:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isAB){
                                fengmianPath1 = jsonObject.get("result").getAsString();
                                Glide.with(ZhengJianXinXiActivity.this)
                                        .load(path)
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
                                        .into(f1);
                                kkk1.setVisibility(View.GONE);

                            }else {
                                fengmianPath2 = jsonObject.get("result").getAsString();
                                Glide.with(ZhengJianXinXiActivity.this)
                                        .load(path)
                                        .apply(RequestOptions.bitmapTransform(new RoundedCorners( 10)))
                                        .into(f2);
                                kkk2.setVisibility(View.GONE);
                            }

                        }
                    });


                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ZhengJianXinXiActivity.this, "获取数据失败");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });

                }
            }
        });
    }

    //    {
//        "backPhoto": "string",
//            "frontPhoto": "string",
//            "idNumber": "string",
//            "realName": "string"
//    }
    private void link_loging(String uname,String num) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object=new JSONObject();
        try {
            object.put("backPhoto",fengmianPath2);
            object.put("frontPhoto",fengmianPath1);
            object.put("idNumber",num);
            object.put("realName",uname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL+"/user/authenticate");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ZhengJianXinXiActivity.this,"获取数据失败,请检查网络");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog!=null)
                            dialog.dismiss();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog!=null)
                            dialog.dismiss();
                    }
                });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Log.d("AllConnects", "身份证:" + ss);
                    Gson gson = new Gson();
                   // LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    if (jsonObject.get("code").getAsInt()==1) {
                        ToastUtils.showInfo(ZhengJianXinXiActivity.this,jsonObject.get("desc").getAsString());
                        EventBus.getDefault().post(new MsgWarp(3333,"审核"));
                        BaoCunBean baoCunBean=MyApplication.myApplication.getBaoCunBean();
                        if (baoCunBean!=null){
                            baoCunBean.setAuth(1);
                            baoCunBean.setRealName(uname);
                            baoCunBean.setIdNumber(num);
                            MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                        }
                        finish();
                    }else {
                        ToastUtils.showInfo(ZhengJianXinXiActivity.this,jsonObject.get("desc").getAsString());
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ZhengJianXinXiActivity.this,e.getMessage() + "异常");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog!=null)
                                dialog.dismiss();
                        }
                    });

                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
