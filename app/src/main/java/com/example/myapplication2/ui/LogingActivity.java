package com.example.myapplication2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.MyApplication;
import com.example.myapplication2.R;
import com.example.myapplication2.beans.BaoCunBean;
import com.example.myapplication2.beans.LogingBe;
import com.example.myapplication2.cookies.CookiesManager;
import com.example.myapplication2.utils.Consts;
import com.example.myapplication2.utils.GsonUtil;
import com.example.myapplication2.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LogingActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .writeTimeout(18000, TimeUnit.MILLISECONDS)
            .connectTimeout(18000, TimeUnit.MILLISECONDS)
            .readTimeout(18000, TimeUnit.MILLISECONDS)
				    .cookieJar(new CookiesManager())
            //        .retryOnConnectionFailure(true)
            .build();

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.yingcang)
    ImageView yingcang;
    @BindView(R.id.yanzhengmadenglu)
    TextView yanzhengmadenglu;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.denglu)
    Button denglu;
    private static final String APP_ID = "wxa118e3198ef780bd";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);
        ButterKnife.bind(this);



    }




    private boolean isC=false;

    @OnClick({R.id.yingcang, R.id.yanzhengmadenglu, R.id.weixin,R.id.denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yingcang:
                if (!isC)  {
                    isC=true;
                    pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    isC=false;
                    pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
            case R.id.yanzhengmadenglu:
                startActivity(new Intent(LogingActivity.this,LogingActivity_zhuce.class));

                break;
            case R.id.weixin:

                IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID, true);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);

                break;
            case R.id.denglu:
                link_loging(phone.getText().toString().trim(),pwd.getText().toString().trim());
                break;
        }
    }


    //查询是否开门
    private void link_loging(String uname,String pwd) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();
        JSONObject object=new JSONObject();
        try {
            object.put("uname",uname);
            object.put("pwd",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .post(body)
                .url(Consts.URL+"/login/password");
        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(LogingActivity.this,"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    if (logingBe.getCode()==2000) {
                        Box<BaoCunBean> baoCunBeanBox = MyApplication.myApplication.getBaoCunBeanBox();
                        BaoCunBean bean = baoCunBeanBox.get(123456);
                        bean.setImUserSig(logingBe.getResult().getImUserSig());
                        bean.setSdkAppId(logingBe.getResult().getSdkAppId());
                        bean.setIsBind(logingBe.getResult().getIsBind());
                        baoCunBeanBox.put(bean);
                        startActivity(new Intent(LogingActivity.this, MainActivity.class));
                    }else {
                        ToastUtils.showInfo(LogingActivity.this,logingBe.getDesc());
                    }
                    Log.d("AllConnects", "登录:" + ss);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(LogingActivity.this,"获取数据失败");
                }
            }
        });
    }
}
