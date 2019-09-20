package com.shengma.lanjing.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.PaiHangListAdapter;

import com.shengma.lanjing.beans.PaiHangListBean;


import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Request;

import okhttp3.Response;
import okhttp3.ResponseBody;


public class PaiHangListDialog extends DialogFragment {

    private Window window;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private PaiHangListAdapter paiHangListAdapter;
    private List<PaiHangListBean.ResultBean> yongHuListBeanList=new ArrayList<>();
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View  view = inflater.inflate(R.layout.paihang_dialog, null);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        paiHangListAdapter=new PaiHangListAdapter(yongHuListBeanList);
        recyclerView.setAdapter(paiHangListAdapter);
       // refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                page+=1;
                link_u();
            }
        });

        link_u();
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

    private void link_u() {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie", "JSESSIONID=" + MyApplication.myApplication.getBaoCunBean().getSession())
                .get()
                .url(Consts.URL + "/anchor/rank?page="+page+"&pageSize=18");
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
                    Log.d("AllConnects", "获取主播排行" + ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    PaiHangListBean logingBe = gson.fromJson(jsonObject, PaiHangListBean.class);
                    if (getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (PaiHangListBean.ResultBean resultBean:logingBe.getResult()){
                                    yongHuListBeanList.add(resultBean);
                                }
                                paiHangListAdapter.notifyDataSetChanged();
                                refreshLayout.finishLoadMore(400/*,false*/);//传入false表示加载失败
                            }
                        });
                    }


                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                    ToastUtils.showError(getActivity(), "获取数据失败");
                }
            }
        });
    }


}
