package com.shengma.lanjing.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.LogingBe;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class XiuGaiMiMaActivity extends AppCompatActivity {
    ZLoadingDialog dialog;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.mima1)
    EditText mima1;
    @BindView(R.id.mima2)
    EditText mima2;
    @BindView(R.id.wancheng)
    Button wancheng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai_mi_ma);
        ButterKnife.bind(this);

    }


    private void link_loging(String uname) {
        dialog = new ZLoadingDialog(XiuGaiMiMaActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                .show();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("pwd", uname)
                .add("verifyPwd", uname)
                .build();

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("username", "")
//                .addFormDataPart("password", "")
//                .build();


        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/user/pwd/change");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(XiuGaiMiMaActivity.this, "获取数据失败,请检查网络");
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
                    }
                });
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "修改密码:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                   // Gson gson = new Gson();
                    ToastUtils.showInfo(XiuGaiMiMaActivity.this, jsonObject.get("desc").getAsString());
                  if (jsonObject.get("code").getAsInt()==1){
                      finish();
                  }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(XiuGaiMiMaActivity.this, "获取数据失败");
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

    @OnClick({R.id.fanhui, R.id.wancheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.wancheng:
                if (!mima1.getText().toString().trim().equals("") && !mima2.getText().toString().trim().equals("")){
                    if (mima1.getText().toString().trim().equals(mima2.getText().toString().trim()))
                    { link_loging(mima1.getText().toString().trim());

                    } else {
                        ToastUtils.showError(XiuGaiMiMaActivity.this, "密码不一致");
                    }

                }else {
                    ToastUtils.showError(XiuGaiMiMaActivity.this, "密码为空");
                }
                break;
        }
    }
}
