package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.BaoCunBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.beans.PuTongInfio;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.InputMethodUtils;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class YongHuXinxiDialog extends DialogFragment {

    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.guanbi)
    ImageView guanbi;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.xingbie)
    ImageView xingbie;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.dengji2)
    TextView dengji2;
    @BindView(R.id.myid)
    TextView myid;
    @BindView(R.id.fensi)
    TextView fensi;
    @BindView(R.id.guanzhu)
    TextView guanzhu;
    @BindView(R.id.textView2)
    LinearLayout textView2;
    @BindView(R.id.xingguang)
    TextView xingguang;
    @BindView(R.id.bianji)
    TextView bianji;
    @BindView(R.id.guanliyuan)
    TextView guanliyuan;
    @BindView(R.id.swich1)
    SwitchCompat swich1;
    @BindView(R.id.swich2)
    SwitchCompat swich2;
    @BindView(R.id.jingyan)
    TextView jingyan;
    @BindView(R.id.textView1)
    LinearLayout textView1;
    @BindView(R.id.textView3)
    LinearLayout textView3;
    @BindView(R.id.tongzhita)
    TextView tongzhita;
    private Window window;
    private Unbinder unbinder;
    private String id;
    private String zhuboid,namess=null;
    private boolean isKYJY;

    public YongHuXinxiDialog(String zhuboid, String id,boolean jkjk) {
        this.id = id;
        this.zhuboid = zhuboid;
        isKYJY=jkjk;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.yonghuxinxi_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        link_userinfo(zhuboid, id);
        link_chaxunGLY(zhuboid, id);
        link_chaxunJY(zhuboid, id);
        swich1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BaoCunBean baoCunBean = MyApplication.myApplication.getBaoCunBean();
                if (baoCunBean.getUserid() != Integer.parseInt(zhuboid)) {
                    swich1.setChecked(!isChecked);
                    if (getActivity() != null)
                        ToastUtils.showError(getActivity(), "您不能设置管理员");
                } else {
                    if (isChecked) {
                        link_shezhiGLY(zhuboid, id);
                    } else {
                        link_quxiaGLY(zhuboid, id);
                    }
                }
            }
        });
        swich2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isKYJY){
                    if (isChecked) {
                        link_shezhiJY(zhuboid, id);
                    } else {
                        link_quxiaoJY(zhuboid, id);
                    }
                }else {
                    swich2.setChecked(!isChecked);
                    if (getActivity() != null)
                        ToastUtils.showError(getActivity(), "您不能设置禁言");
                }


            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window != null) {
            // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
            window.setBackgroundDrawableResource(android.R.color.transparent);
            // 设置动画
            window.setWindowAnimations(R.style.bottomDialog);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
            params.width = getResources().getDisplayMetrics().widthPixels;
            window.setAttributes(params);
        }

    }


    private void link_userinfo(String id1, String id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/user/info/" + id1 + "/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "查询个人信息:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    namess=logingBe.getResult().getNickname();
                                    name.setText(logingBe.getResult().getNickname());
                                    dengji.setText("Lv." + logingBe.getResult().getUserLevel());
                                    dengji2.setText("Lv." + logingBe.getResult().getAnchorLevel());
                                    myid.setText("ID:" + logingBe.getResult().getId());
                                    if (logingBe.getResult().getSex() == 1) {
                                        xingbie.setBackgroundResource(R.drawable.nan);
                                    } else {
                                        xingbie.setBackgroundResource(R.drawable.nv);
                                    }
                                    fensi.setText(logingBe.getResult().getFans() + "");
                                    guanzhu.setText(logingBe.getResult().getIdols() + "");
                                    double xg = logingBe.getResult().getTotal();
                                    if (xg >= 10000) {
                                        xingguang.setText(Utils.doubleToString(xg / 10000.0) + "万");
                                    } else {
                                        xingguang.setText(xg + "");
                                    }
                                    Glide.with(getActivity())
                                            .load(logingBe.getResult().getHeadImage())
                                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                            .into(touxiang);
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }


    private void link_chaxunGLY(String zhuboid, String id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/im/" + id + "?group=" + zhuboid);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "查询是否管理员:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    //  Gson gson = new Gson();//{"code":0,"desc":"不是管理员","total":0}
                    //  PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (jsonObject.get("code").getAsInt() == 0) {//0是非管理员
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swich1.setChecked(false);
                                    guanliyuan.setText("非管理员");
                                }
                            });
                    } else if (jsonObject.get("code").getAsInt() == 1) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swich1.setChecked(true);
                                    guanliyuan.setText("管理员");

                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_quxiaGLY(String zhuboid, String id) {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("group", zhuboid)
                .add("id", id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/im/cancel/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "取消管理员:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    guanliyuan.setText("非管理员");
                                    swich1.setChecked(false);
                                    EventBus.getDefault().post(new MsgWarp(3368, "0"));
                                }
                            });
                    } else {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swich1.setChecked(true);
                                    guanliyuan.setText("管理员");
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_shezhiGLY(String zhuboid, String id) {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("group", zhuboid)
                .add("id", id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/im/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "设置管理员:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        guanliyuan.setText("管理员");
                                        swich1.setChecked(true);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    EventBus.getDefault().post(new MsgWarp(3368, "1"));
                                }
                            });
                    } else {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        swich1.setChecked(false);
                                        guanliyuan.setText("非管理员");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_shezhiJY(String zhuboid, String id) {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("group", zhuboid)
                .add("id", id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/im/ban/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "设置禁言:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        swich2.setChecked(true);
                                        jingyan.setText("禁言");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    EventBus.getDefault().post(new MsgWarp(3369, "1"));
                                }
                            });
                    } else {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        swich2.setChecked(false);
                                        jingyan.setText("未禁言");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_quxiaoJY(String zhuboid, String id) {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("group", zhuboid)
                .add("id", id)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/im/ban/cancel/" + id);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "取消禁言:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PuTongInfio logingBe = gson.fromJson(jsonObject, PuTongInfio.class);
                    if (logingBe.getCode() == 2000) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swich2.setChecked(false);
                                    jingyan.setText("未禁言");
                                    EventBus.getDefault().post(new MsgWarp(3369, "0"));
                                }
                            });
                    } else {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swich2.setChecked(true);
                                    jingyan.setText("禁言");
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

    private void link_chaxunJY(String zhuboid, String id) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/im/ban/" + id + "?group=" + zhuboid);
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                //  ToastUtils.showError(WoDeZiLiaoActivity.this, "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "查询是否禁言:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt() == 0) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    jingyan.setText("未禁言");
                                    swich2.setChecked(false);

                                }
                            });
                    } else if (jsonObject.get("code").getAsInt() == 1) {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    jingyan.setText("禁言");
                                    swich2.setChecked(true);

                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    // ToastUtils.showError(BoFangActivity.this, "获取数据失败");

                }
            }
        });
    }

//    @OnClick(R.id.bianji)
//    public void onViewClicked() {
//        SheZhiDialog dialog = new SheZhiDialog();
//        dialog.show(getFragmentManager(), "ddffgdfg");
//        dismiss();
//    }

    @OnClick(R.id.tongzhita)
    public void onViewClicked() {
        Log.d("ZhiBoActivity",  "异常");
        if (namess==null){
            if (getActivity()!=null)
            ToastUtils.showError(getActivity(), "信息未加载完成");
            return;
        }
        dismiss();
        EventBus.getDefault().post(new MsgWarp(887700, "@"+namess+" "));
        Log.d("ZhiBoActivity",  "异常");
    }
}
