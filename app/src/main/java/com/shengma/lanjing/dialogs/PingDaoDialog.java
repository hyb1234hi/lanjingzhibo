package com.shengma.lanjing.dialogs;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.DisplayUtils;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.GridDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class PingDaoDialog extends DialogFragment implements View.OnClickListener {

    private Window window;
    private RecyclerView recyclerView;
    private PingDaoAdapter adapter;
    private List<LiveType.ResultBean> resultBeanList=new ArrayList<>();
    private ImageView guanbi;
    private Button wancheng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.pingdao_dialog, null);
        recyclerView=view.findViewById(R.id.recyclerview);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new PingDaoAdapter(resultBeanList);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        guanbi=view.findViewById(R.id.guanbi);
        guanbi.setOnClickListener(this);
        wancheng=view.findViewById(R.id.wancheng);
        wancheng.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guanbi:
                dismiss();
                break;
            case R.id.wancheng:
                for (int i=0;i<resultBeanList.size();i++){
                    if (resultBeanList.get(i).getIsXZ()==1){
                        EventBus.getDefault().post(new MsgWarp(i,resultBeanList.get(i).getName()));
                        break;
                    }
                }
                dismiss();
                break;



       }
    }



    private void link_live_type() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
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




}
