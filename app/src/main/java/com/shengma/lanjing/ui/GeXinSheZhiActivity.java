package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.LogingBe;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.dialogs.TuiChuDialog2;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GeXinSheZhiActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl4)
    RelativeLayout rl4;
    @BindView(R.id.rl5)
    RelativeLayout rl5;
    @BindView(R.id.tuichu)
    LinearLayout tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_xin_she_zhi);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.fanhui, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(GeXinSheZhiActivity.this,ZhangHaoAnQuanActivity.class));
                break;
            case R.id.rl2:

                break;
            case R.id.rl3:

                break;
            case R.id.rl4:

                break;
            case R.id.rl5:

                break;
            case R.id.tuichu:
                TuiChuDialog2 tuiChuDialog2=new TuiChuDialog2(GeXinSheZhiActivity.this);
                tuiChuDialog2.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        link_logout();
                        MyApplication.myApplication.getBaoCunBeanBox().removeAll();
                        BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBeanBox().get(123456L);
                        if (baoCunBean == null) {
                            baoCunBean = new BaoCunBean();
                            baoCunBean.setId(123456L);
                            MyApplication.myApplication.getBaoCunBeanBox().put(baoCunBean);
                        }

                        startActivity(new Intent(GeXinSheZhiActivity.this,LogingActivity.class));
                        tuiChuDialog2.dismiss();
                        EventBus.getDefault().post(new MsgWarp(1002,""));
                        finish();
                    }
                });
                tuiChuDialog2.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tuiChuDialog2.dismiss();
                    }
                });
                tuiChuDialog2.show();
                break;
        }
    }

    private void link_logout() {
       // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("uname", "")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL+"/logout");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                   // JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Log.d("AllConnects", "退出:" + ss);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }
}
