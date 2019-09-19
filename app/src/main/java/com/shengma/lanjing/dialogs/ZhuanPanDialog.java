package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.beans.ChouJiangBean;
import com.shengma.lanjing.beans.ChouJiangBs;

import com.shengma.lanjing.beans.MsgWarp;

import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.PieView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ZhuanPanDialog extends DialogFragment  {

    private Window window;
    private PieView pieView;
    private ImageView guanbi;
    private boolean isR;
    private List<ChouJiangBean.ResultBean> beanList=new ArrayList<>();
    private ImageView kaishi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.zhuanpan_dialog, null);
        EventBus.getDefault().register(this);
        kaishi=view.findViewById(R.id.kaishi);
        guanbi=view.findViewById(R.id.guanbi);
        pieView=view.findViewById(R.id.pieview);
        pieView.setListener(new PieView.RotateListener() {
            @Override
            public void value(String s) {
                Log.d("ZhuanPanDialog", s);
                isR=false;
                if (getActivity()!=null)
                ToastUtils.showInfo(getActivity(),"您抽中了:"+s);
            }
        });
       // pieView.rotate(1);
        link_iu();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp) {
        if (msgWarp.getType()==2222){
            if (!isR){
                isR=true;
                link_kaishi();

            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效
        window = getDialog().getWindow();
        if (window!=null){
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


    private void link_iu() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/lottery");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "抽奖" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ChouJiangBean logingBe = gson.fromJson(jsonObject, ChouJiangBean.class);
                    beanList.clear();
                    ChouJiangBean.ResultBean bbb=new ChouJiangBean.ResultBean();
                    bbb.setId(-1);bbb.setGiftUrl("R.drawable.weizhongjiang");
                    bbb.setName("未中奖");
                    logingBe.getResult().add(bbb);
                    beanList.addAll(logingBe.getResult());
                    pieView.setChouJiangBeanList(logingBe.getResult(),getActivity());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            kaishi.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                    ToastUtils.showError(getActivity(), "获取数据失败");
                }
            }
        });
    }

    private void link_kaishi() {
        RequestBody body = null;
        body = new FormBody.Builder()
                .add("", "")
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL + "/lottery");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(), "获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "开始抽奖" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    ChouJiangBs logingBe = gson.fromJson(jsonObject, ChouJiangBs.class);
                    int s=0;
                    for (int i=0;i<beanList.size();i++){
                        s+=1;
                        if (beanList.get(i).getId()==logingBe.getResult().getId()){
                            Log.d("ZhuanPanDialog", "beanList:" + beanList.get(i).getId());
                             s = s +2;

                            pieView.rotate(s);
                            break;
                        }
                    }
                    Log.d("ZhuanPanDialog", "s:" + s);
//                    if (s==9){
//                        pieView.rotate(1);
//                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(), "获取数据失败");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
