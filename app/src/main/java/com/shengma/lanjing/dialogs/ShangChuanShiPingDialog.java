package com.shengma.lanjing.dialogs;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.PingDaoAdapter;
import com.shengma.lanjing.beans.LiveType;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.ui.KaiBoActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ShangChuanShiPingDialog extends DialogFragment {


    @BindView(R.id.fanhui)
    ImageView fanhui;
    @BindView(R.id.fengmian)
    ImageView fengmian;
    @BindView(R.id.zhuti)
    EditText zhuti;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.wancheng)
    Button wancheng;
    private Window window;
    private Unbinder unbinder;
    private int type = -1;
    private PingDaoAdapter adapter;
    private List<LiveType.ResultBean> resultBeanList=new ArrayList<>();
    private PhotoDialog photoDialog;
    private ZLoadingDialog dialog;
    private String fengmianPath;
    private String vidoPath;

    public ShangChuanShiPingDialog(String path) {
        vidoPath=path;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.shipingshangchuan_dialog, null);
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new PingDaoAdapter(resultBeanList);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
//        int firstAndLastColumnW = DisplayUtils.dp2px(getActivity(), 8);
//        int firstRowTopMargin = DisplayUtils.dp2px(getActivity(), 8);
//        GridDividerItemDecoration gridDividerItemDecoration =
//                new GridDividerItemDecoration(getActivity(), firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
//        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
//        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
//        recyclerView.addItemDecoration(gridDividerItemDecoration);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + position);
                for (int i=0;i<resultBeanList.size();i++){
                    if (i==position){
                        resultBeanList.get(i).setIsXZ(1);
                    }else {
                        resultBeanList.get(i).setIsXZ(0);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        link_live_type();

        return view;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photo(MsgWarp msgWarp) {
        if (msgWarp.getType() == 1003) {
            if (getActivity()!=null)
            dialog = new ZLoadingDialog(getActivity());
            dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                    .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                    .setHintText("上传中...")
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.WHITE)  // 设置字体颜色
                    .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                    .show();
            link_loging(msgWarp.getMsg());
            Log.d("KaiBoActivity", msgWarp.getMsg());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.fanhui, R.id.fengmian, R.id.wancheng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                dismiss();
                break;
            case R.id.fengmian:
                photoDialog = new PhotoDialog();
                if (getFragmentManager()!=null)
                photoDialog.show(getFragmentManager(), "photodialog");

                break;
            case R.id.wancheng:
                if (fengmianPath==null || fengmianPath.equals("")){
                    if (getActivity()!=null)
                    ToastUtils.showInfo(getActivity(),"请先上传封面图片");
                    return;
                }
                for (int i=0;i<resultBeanList.size();i++){
                    if (resultBeanList.get(i).getIsXZ()==1){
                       type=i;
                        break;
                    }
                }
                if (type==-1){
                    if (getActivity()!=null)
                        ToastUtils.showInfo(getActivity(),"请选择频道");
                    return;
                }
                if (zhuti.getText().toString().trim().equals("")){
                    if (getActivity()!=null)
                        ToastUtils.showInfo(getActivity(),"请填写主题");
                    return;
                }
                if (getActivity()!=null)
                dialog = new ZLoadingDialog(getActivity());
                dialog.setLoadingBuilder(Z_TYPE.LEAF_ROTATE)//设置类型
                        .setLoadingColor(Color.parseColor("#FF3EE1F7"))//颜色
                        .setHintText("上传中...")
                        .setHintTextSize(16) // 设置字体大小 dp
                        .setHintTextColor(Color.WHITE)  // 设置字体颜色
                        .setDurationTime(0.6) // 设置动画时间百分比 - 0.5倍
                        .setDialogBackgroundColor(Color.parseColor("#bb111111")) // 设置背景色，默认白色
                        .show();
                link_void(vidoPath);

                break;
        }
    }

    private void link_live_type() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL+"/live/type");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "关注列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    LiveType bean = gson.fromJson(jsonObject, LiveType.class);
                    if (bean.getCode()==2000) {
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultBeanList.clear();
                                    resultBeanList.addAll(bean.getResult());
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(),"获取数据失败");

                }
            }
        });
    }



    private void link_loging(String path) {
       // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("img", System.currentTimeMillis() + ".png", fileBody)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/user/upload/img");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                            ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });
                }
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传图片:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
//                    Gson gson = new Gson();
//                    LogingBe logingBe = gson.fromJson(jsonObject, LogingBe.class);
                    fengmianPath=jsonObject.get("result").getAsString();
                    if (getActivity()!=null)
                     getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fengmian.setImageBitmap(BitmapFactory.decodeFile(path));
                            if (photoDialog != null)
                                photoDialog.dismiss();
                        }
                    });

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null)
                                    dialog.dismiss();
                                ToastUtils.showError(getActivity(),e.getMessage()+"");
                            }
                        });
                    }
                }
            }
        });
    }


    private void link_void(String path) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = null;
//        body = new FormBody.Builder()
//                .add("uname", uname)
//                .add("pwd", pwd)
//                .build();
//        JSONObject object=new JSONObject();
//        try {
//            object.put("uname",uname);
//            object.put("pwd",pwd);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //RequestBody body = RequestBody.create(object.toString(),JSON);
        RequestBody fileBody = RequestBody.create(new File(path), MediaType.parse("application/octet-stream"));
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video", System.currentTimeMillis() + ".mp4", fileBody)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(requestBody)
                .url(Consts.URL + "/video");
        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                            ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("AllConnects", "上传图片:" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    if (jsonObject.get("code").getAsInt()==2000){
                        link_save(jsonObject.get("result").getAsString());
                    }else {
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog != null)
                                        dialog.dismiss();
                                    ToastUtils.showError(getActivity(),"上传失败");
                                }
                            });
                        }
                    }

                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null)
                                    dialog.dismiss();
                                ToastUtils.showError(getActivity(),e.getMessage()+"");
                            }
                        });
                    }
                }
            }
        });
    }


    private void link_save(String videoUrl) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        JSONObject object=new JSONObject();
        try {
            object.put("imgUrl",fengmianPath);
            object.put("title",zhuti.getText().toString().trim());
            object.put("type",type);
            object.put("videoUrl",videoUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(),JSON);
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .post(body)
                .url(Consts.URL+"/video/upload");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                            ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    });
                }
                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "保存视频"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();

                    if (jsonObject.get("code").getAsInt()==1) {
                        if (getActivity()!=null){
                           ToastUtils.showInfo(getActivity(),"上传成功");
                           ShangChuanShiPingDialog.this.dismiss();
                        }
                    }else {
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog != null)
                                        dialog.dismiss();
                                    ToastUtils.showError(getActivity(),jsonObject.get("desc").getAsString());
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog != null)
                                    dialog.dismiss();
                                ToastUtils.showError(getActivity(),e.getMessage()+"");
                            }
                        });
                    }

                }
            }
        });
    }
}
