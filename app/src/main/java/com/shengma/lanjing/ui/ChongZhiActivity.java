package com.shengma.lanjing.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.QianBaoBean;
import com.shengma.lanjing.beans.WXBean;
import com.shengma.lanjing.beans.ZFBBean;
import com.shengma.lanjing.beans.ZhiFuBB;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChongZhiActivity extends AppCompatActivity {

    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.yue)
    TextView yue;
    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.textView1)
    LinearLayout textView1;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.textView2)
    LinearLayout textView2;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.textView3)
    LinearLayout textView3;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.textView4)
    LinearLayout textView4;
    @BindView(R.id.im5)
    ImageView im5;
    @BindView(R.id.textView5)
    LinearLayout textView5;
    @BindView(R.id.im6)
    ImageView im6;
    @BindView(R.id.textView6)
    LinearLayout textView6;
    @BindView(R.id.im7)
    ImageView im7;
    @BindView(R.id.textView7)
    LinearLayout textView7;
    @BindView(R.id.textView8)
    LinearLayout textView8;
    @BindView(R.id.textView9)
    LinearLayout textView9;
    @BindView(R.id.zhifuET)
    EditText zhifuET;
    @BindView(R.id.shifu)
    TextView shifu;
    @BindView(R.id.zhifu)
    Button zhifu;
    List<ZhiFuBB> zhiFuBBS = new ArrayList<>();
    @BindView(R.id.radioGroup_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.xieyi2)
    TextView xieyi2;
    private float monery = 0;
    private int type = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chong_zhi);
        ButterKnife.bind(this);
        BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
        if (baoCunBean != null) {
            Glide.with(ChongZhiActivity.this)
                    .load(baoCunBean.getHeadImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(touxiang);
            name.setText(baoCunBean.getNickname() + "");
            yue.setText("鲸币余额:0");

        }

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.nan:
                        //当用户点击男性按钮时执行的代码
                        type = 1;
                        break;
                    case R.id.nv:
                        //当用户点击女性按钮时执行的代码
                        type = 2;
                        break;

                }
            }
        });

        zhiFuBBS.add(new ZhiFuBB(textView1, im1));
        zhiFuBBS.add(new ZhiFuBB(textView2, im2));
        zhiFuBBS.add(new ZhiFuBB(textView3, im3));
        zhiFuBBS.add(new ZhiFuBB(textView4, im4));
        zhiFuBBS.add(new ZhiFuBB(textView5, im5));
        zhiFuBBS.add(new ZhiFuBB(textView6, im6));
        zhiFuBBS.add(new ZhiFuBB(textView7, im7));
        zhifuET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chongzhi2();
                shifu.setText("实付:¥" + charSequence.toString());
                monery = 0;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //  link_qianbao();


    }

    @Override
    protected void onResume() {
        super.onResume();
        textView1.setFocusableInTouchMode(true);
        zhifuET.clearFocus();
        link_qianbao();
    }

    private void link_qianbao() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/user/wallet");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ChongZhiActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("ChongZhiActivity", "用户信息" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    QianBaoBean userInfoBean = gson.fromJson(jsonObject, QianBaoBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            yue.setText("鲸币余额:" + userInfoBean.getResult().getBalance());

                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ChongZhiActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_wx(String qq) {
        ZLoadingDialog dialog = new ZLoadingDialog(ChongZhiActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                .show();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("amount", qq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ChongZhiActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/wxpay");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ChongZhiActivity.this, "获取数据失败,请检查网络");
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("ChongZhiActivity", "微信支付" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    WXBean wxBean = gson.fromJson(jsonObject, WXBean.class);
                    if (wxBean.getCode() == 2000) {
                        IWXAPI api = WXAPIFactory.createWXAPI(ChongZhiActivity.this, Consts.APP_ID, false);
                        ;
                        PayReq request = new PayReq();
                        request.appId = Consts.APP_ID;
                        request.partnerId = wxBean.getResult().getPartnerid();
                        request.prepayId = wxBean.getResult().getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = wxBean.getResult().getNoncestr();
                        request.timeStamp = wxBean.getResult().getTimestamp();
                        request.sign = wxBean.getResult().getSign();
                        api.sendReq(request);
                    } else {
                        ToastUtils.showError(ChongZhiActivity.this, wxBean.getDesc());
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ChongZhiActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_zfb(String qq) {
        ZLoadingDialog dialog = new ZLoadingDialog(ChongZhiActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.WHITE)  // 设置字体颜色
                .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                .show();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("amount", qq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ChongZhiActivity", object.toString());
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/alipay");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                ToastUtils.showError(ChongZhiActivity.this, "获取数据失败,请检查网络");
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null)
                            dialog.dismiss();
                    }
                });
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("ChongZhiActivity", "支付宝支付" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ZFBBean wxBean = gson.fromJson(jsonObject, ZFBBean.class);
                    if (wxBean.getCode() == 2000) {

                        PayTask alipay = new PayTask(ChongZhiActivity.this);
                        Map<String, String> result = alipay.payV2(wxBean.getResult(), true);

                        Log.d("ChongZhiActivity", "支付宝支付:" + result.toString());
                        Message msg = new Message();
                        msg.what = 111;
                        msg.obj = result;
                        mHandler.sendMessage(msg);


                    } else {
                        ToastUtils.showError(ChongZhiActivity.this, wxBean.getDesc());
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    ToastUtils.showError(ChongZhiActivity.this, "获取数据失败");

                }
            }
        });
    }

    @OnClick({R.id.fanhui, R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
            R.id.textView5, R.id.textView6, R.id.textView7, R.id.zhifu,R.id.xieyi2})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fanhui:
                finish();
                break;
            case R.id.textView1:
                chongzhi();
                textView1.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im1.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥58");
                monery = 58f;
                break;
            case R.id.textView2:
                chongzhi();
                textView2.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im2.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥98");
                monery = 98f;
                break;
            case R.id.textView3:
                chongzhi();
                textView3.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im3.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥598");
                monery = 598f;
                break;
            case R.id.textView4:
                chongzhi();
                textView4.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im4.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥1998");
                monery = 1998f;
                break;
            case R.id.textView5:
                chongzhi();
                textView5.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im5.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥3998");
                monery = 3998f;
                break;
            case R.id.textView6:
                chongzhi();
                textView6.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im6.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥5998");
                monery = 5998f;
                break;
            case R.id.textView7:
                chongzhi();
                textView7.setBackgroundResource(R.drawable.yuanxian_yinying2);
                im7.setVisibility(View.VISIBLE);
                shifu.setText("实付:¥9998");
                monery = 9998f;
                break;
            case R.id.zhifu:
                if (type == 1) {//微信
                    Log.d("ChongZhiActivity", "微信");
                    if (!zhifuET.getText().toString().trim().equals("")) {
                        link_wx(zhifuET.getText().toString().trim());
                    } else {
                        Log.d("ChongZhiActivity", "monery:" + monery);
                        if (monery != 0) {
                            link_wx(monery + "");
                        } else {
                            ToastUtils.showInfo(ChongZhiActivity.this, "请选择充值金额");
                        }
                    }
                } else {//支付宝
                    Log.d("ChongZhiActivity", "支付宝");
                    if (!zhifuET.getText().toString().trim().equals("")) {
                        link_zfb(zhifuET.getText().toString().trim());
                    } else {
                        Log.d("ChongZhiActivity", "monery:" + monery);
                        if (monery != 0) {
                            link_zfb(monery + "");
                        } else {
                            ToastUtils.showInfo(ChongZhiActivity.this, "请选择充值金额");
                        }
                    }

                }
                break;
            case R.id.xieyi2:
                Intent intent =new Intent(ChongZhiActivity.this,XieYiActivity.class);
                intent.putExtra("file","xieyi1.pdf");
                startActivity(intent);

                break;
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 111) {
                Log.d("ChongZhiActivity", "msg.obj:" + msg.obj);
            }

        }

        ;
    };

    private void chongzhi() {
        for (ZhiFuBB vv : zhiFuBBS) {
            vv.getKuang().setBackgroundResource(R.drawable.yuanxian_yinying);
            vv.getIm().setVisibility(View.GONE);
            vv.setA(false);
        }
        zhifuET.setText("");
    }

    private void chongzhi2() {
        for (ZhiFuBB vv : zhiFuBBS) {
            vv.getKuang().setBackgroundResource(R.drawable.yuanxian_yinying);
            vv.getIm().setVisibility(View.GONE);
            vv.setA(false);
        }
    }

}
