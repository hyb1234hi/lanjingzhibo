package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.LogingBe;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.PhoneCode;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LogingActivity_yzm extends AppCompatActivity {

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();
    @BindView(R.id.yanzhengma)
    TextView yanzhengma;
    @BindView(R.id.yifasong)
    TextView yifasong;
    @BindView(R.id.pc_1)
    PhoneCode pc1;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    private CountDownTimer timer;
    private String wx="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_yzm);
        ButterKnife.bind(this);
        yifasong.setText("已发送到:" + getIntent().getStringExtra("phone"));
        wx=getIntent().getStringExtra("wx");
        pc1 = (PhoneCode) findViewById(R.id.pc_1);
        EventBus.getDefault().register(this);
        //注册事件回调（根据实际需要，可写，可不写）
        pc1.setOnInputListener(new PhoneCode.OnInputListener() {
            @Override
            public void onSucess(String code) {
                Log.d("LogingActivity_yzm", code);
                //输完后才会回调
                if (wx!=null && wx.equals("wx")){
                    link_bind(getIntent().getStringExtra("phone"),code);
                }else {
                    //手机注册
                    link_sjbind(getIntent().getStringExtra("phone"),code);
                }
            }

            @Override
            public void onInput() {
                Log.d("LogingActivity_yzm", "ddddd");
                //每次输入都会回调

            }
        });

        timer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                yanzhengma.setEnabled(false);
                yanzhengma.setText((millisUntilFinished / 1000) + "秒后可重发");
            }

            @Override
            public void onFinish() {
                yanzhengma.setEnabled(true);
                yanzhengma.setText("重新获取验证码");
            }
        };
        timer.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        link_send(getIntent().getStringExtra("phone"));
    }

    @OnClick({R.id.yanzhengma,R.id.fanhui})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.yanzhengma) {

                timer = new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        yanzhengma.setEnabled(false);
                        yanzhengma.setText((millisUntilFinished / 1000) + "秒后可重发");
                    }

                    @Override
                    public void onFinish() {
                        yanzhengma.setEnabled(true);
                        yanzhengma.setText("重新获取验证码");
                    }
                };
                timer.start();
            link_send(getIntent().getStringExtra("phone"));
        }
        if (view.getId() == R.id.fanhui) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        timer.cancel();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp){

        if (msgWarp.getType()==1002){
            finish();
        }
    }


    private void link_send(String phone) {
       // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("phone", phone)
                .build();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("phone",phone);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
       // Log.d("LogingActivity", object.toString());
    //    RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(Consts.URL+"/login/send");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "验证码"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    ToastUtils.showInfo(LogingActivity_yzm.this,jsonObject.get("desc").getAsString());
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败");
                }
            }
        });
    }


    private void link_bind(String phone,String code) {
       //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("phone", phone)
                .add("code", code)
                .build();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("code",code);
//            object.put("phone",phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // Log.d("LogingActivity", object.toString());
         //   RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(Consts.URL+"/user/bind");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "微信绑定手机"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==1){
                        startActivity(new Intent(LogingActivity_yzm.this,LogingActivity_mima.class));
                    }
                    ToastUtils.showInfo(LogingActivity_yzm.this,jsonObject.get("desc").getAsString());
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败");
                }
            }
        });
    }


    private void link_sjbind(String phone,String code) {
        //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("phone", phone)
                .add("verifyCode", code)
                .build();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("code",code);
//            object.put("phone",phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // Log.d("LogingActivity", object.toString());
        //   RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")

                .post(body)
                .url(Consts.URL+"/login/phone");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "手机号注册登录"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    if (logingBe.getCode()==2000) {
                        Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
                        BaoCunBean bean = baoCunBeanBox.get(123456);
                        bean.setImUserSig(logingBe.getResult().getImUserSig());
                        bean.setSdkAppId(logingBe.getResult().getSdkAppId());
                        bean.setIsBind(logingBe.getResult().getIsBind());
                        bean.setSession(logingBe.getResult().getSession());
                        baoCunBeanBox.put(bean);
                        link_chaxun();
                    }else {
                        ToastUtils.showInfo(LogingActivity_yzm.this,logingBe.getDesc());
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败");
                }
            }
        });
    }

    private void link_chaxun() {
        //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("", "")
                .build();
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("code",code);
//            object.put("phone",phone);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        // Log.d("LogingActivity", object.toString());
        //   RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(Consts.URL+"/user/pwd/exist");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "查询密码"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==0){
                        startActivity(new Intent(LogingActivity_yzm.this,LogingActivity_mima.class));
                    }else {
                        EventBus.getDefault().post(new MsgWarp(1002,""));
                        startActivity(new Intent(LogingActivity_yzm.this,MainActivity.class));
                    }
                    ToastUtils.showInfo(LogingActivity_yzm.this,jsonObject.get("desc").getAsString());
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity_yzm.this,"获取数据失败");
                }
            }
        });
    }

}
