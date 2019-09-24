package com.shengma.lanjing.ui;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class LogingActivity_mima extends AppCompatActivity {

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
            .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();
    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.mima1)
    EditText mima1;
    @BindView(R.id.mima2)
    EditText mima2;
    @BindView(R.id.denglu)
    Button denglu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_mima);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp){

        if (msgWarp.getType()==1002){
            finish();
        }
    }


    @OnClick({R.id.fanhui, R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.denglu:
                link_mima(mima1.getText().toString().trim(),mima2.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void link_mima(String phone,String code) {
        //  MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("passwd", phone)
                .add("verifyPwd", code)
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
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL+"/user/pwd/set");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity_mima.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "设置密码"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==1){
                        EventBus.getDefault().post(new MsgWarp(1002,""));
                        startActivity(new Intent(LogingActivity_mima.this,MainActivity.class));
                    }
                    ToastUtils.showInfo(LogingActivity_mima.this,jsonObject.get("desc").getAsString());
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity_mima.this,"获取数据失败");
                }
            }
        });
    }

}
