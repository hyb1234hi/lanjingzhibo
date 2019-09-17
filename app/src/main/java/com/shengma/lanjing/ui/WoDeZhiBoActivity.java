package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WoDeZhiBoActivity extends AppCompatActivity {

    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.shouru)
    TextView shouru;
    @BindView(R.id.rl1)
    RelativeLayout rl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_de_zhi_bo);
        ButterKnife.bind(this);


    }

    @Override
    public void onResume() {
        super.onResume();
       BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBeanBox().get(123456);
        if (baoCunBean != null) {

            Glide.with(WoDeZhiBoActivity.this)
                        .load(baoCunBean.getHeadImage())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(touxiang);
            //RequestOptions.bitmapTransform(new CircleCrop())//圆形
            //RequestOptions.bitmapTransform(new RoundedCorners( 5))//圆角
            name.setText(baoCunBean.getNickname() + "");
            dengji.setText("Lv." + baoCunBean.getAnchorLevel());

        }

    }


    @OnClick({R.id.fanhui, R.id.rl1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.rl1:
                startActivity(new Intent(WoDeZhiBoActivity.this,ZHiBoShiChangActivity.class));
                break;
        }
    }

    private void link_logout() {

        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/anchor/income");
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
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==2000) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                shouru.setText(jsonObject.get("total").getAsInt()+"");
                            }
                        });
                    }
                    Log.d("AllConnects", "今日收益:" + ss);
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");

                }
            }
        });
    }
}
