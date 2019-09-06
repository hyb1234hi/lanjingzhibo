package com.shengma.lanjing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.QianBaoBean;
import com.shengma.lanjing.beans.UserInfoBean;
import com.shengma.lanjing.cookies.CookiesManager;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;

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
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class QianBaoActivity extends AppCompatActivity {



    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.yue)
    TextView yue;
    @BindView(R.id.chongzhi)
    Button chongzhi;
    @BindView(R.id.jingbi)
    TextView jingbi;
    @BindView(R.id.liwushouyi)
    TextView liwushouyi;
    @BindView(R.id.zhiboshouji)
    TextView zhiboshouji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_bao);
        ButterKnife.bind(this);

        link_qianbao();
    }

    @OnClick({R.id.fanhui, R.id.chongzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.chongzhi:
                startActivity(new Intent(QianBaoActivity.this,ChongZhiActivity.class));
                break;
        }
    }


    private void link_qianbao() {
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
                .url(Consts.URL+"/user/wallet");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(QianBaoActivity.this,"获取数据失败,请检查网络");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "用户信息"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    QianBaoBean userInfoBean = gson.fromJson(jsonObject, QianBaoBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            yue.setText(userInfoBean.getResult().getBalance()+"");
                            liwushouyi.setText(userInfoBean.getResult().getIncome()+"");
                            zhiboshouji.setText(userInfoBean.getResult().getTotal()+"");
                            jingbi.setText(userInfoBean.getResult().getConsumption()+"");
                        }
                    });


                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(QianBaoActivity.this,"获取数据失败");

                }
            }
        });
    }

}
