package com.shengma.lanjing.ui.fargments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shengma.lanjing.MyApplication;
import com.shengma.lanjing.R;
import com.shengma.lanjing.adapters.FuJinAdapter;
import com.shengma.lanjing.beans.FuJinBean;
import com.shengma.lanjing.beans.MsgWarp;
import com.shengma.lanjing.ui.SouSuoActivity;
import com.shengma.lanjing.ui.zhibo.BoFangActivity;
import com.shengma.lanjing.utils.Consts;
import com.shengma.lanjing.utils.DisplayUtils;
import com.shengma.lanjing.utils.GsonUtil;
import com.shengma.lanjing.utils.ToastUtils;
import com.shengma.lanjing.views.GridDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
//    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .writeTimeout(18000, TimeUnit.MILLISECONDS)
//            .connectTimeout(18000, TimeUnit.MILLISECONDS)
//            .readTimeout(18000, TimeUnit.MILLISECONDS)
//            .cookieJar(new CookiesManager())
//            //        .retryOnConnectionFailure(true)
//            .build();
    private int pag=1;
    private String jd="",wd="";
    private List<FuJinBean.ResultBean> beanList=new ArrayList<>();
    private FuJinAdapter adapter;
    private TextView sousuo;
    private TextView rrr;
    private LinearLayout shuju;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_fragment2, container, false);
       rrr=view.findViewById(R.id.rtrr);
       sousuo=view.findViewById(R.id.sousuo);
       EventBus.getDefault().register(this);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        shuju=view.findViewById(R.id.shuju);
        recyclerView=view.findViewById(R.id.recyclerview);
//        //设置进度View的组合颜色，在手指上下滑时使用第一个颜色，在刷新中，会一个个颜色进行切换
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3EE1F7"), Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                link_list();
//            }
//        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                pag=1;
                beanList.clear();
                link_list(1);//刷新
               // refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                pag++;
                link_list(2);//加载更多

               // refreshlayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
            }
        });

        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SouSuoActivity.class));
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter=new FuJinAdapter(beanList);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        int firstAndLastColumnW = DisplayUtils.dp2px(getActivity(), 16);
        int firstRowTopMargin = DisplayUtils.dp2px(getActivity(), 16);
        GridDividerItemDecoration gridDividerItemDecoration =
                new GridDividerItemDecoration(getActivity(), firstAndLastColumnW, firstRowTopMargin, firstRowTopMargin);
        gridDividerItemDecoration.setFirstRowTopMargin(firstRowTopMargin);
        gridDividerItemDecoration.setLastRowBottomMargin(firstRowTopMargin);
        recyclerView.addItemDecoration(gridDividerItemDecoration);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "position:" + beanList.get(position).getId());
                Intent intent=new Intent(getActivity(), BoFangActivity.class);
                intent.putExtra("idid",beanList.get(position).getId());
                intent.putExtra("playPath",beanList.get(position).getPlayUrl());
                startActivity(intent);

            }
        });
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxMSG(MsgWarp msgWarp){
        if (msgWarp.getType()==6666) {//
            jd=msgWarp.getMsg();
            wd=msgWarp.getTemp();
            pag=1;
            beanList.clear();
            link_list(1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        rrr.setFocusableInTouchMode(true);
        sousuo.clearFocus();
    }

    private void link_list(int type) {
        Request.Builder requestBuilder = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Cookie","JSESSIONID="+ MyApplication.myApplication.getBaoCunBean().getSession())
                .get()///live/nearby?latitude=1&longitude=1&page=1&pageSize=10
                .url(Consts.URL+"/live/nearby?latitude="+wd+"&longitude="+jd+"&page="+pag+"&pageSize=10");

        // step 3：创建 Call 对象
        Call call = MyApplication.myApplication.getOkHttpClient().newCall(requestBuilder.build());
        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求失败" + e.getMessage());
                if (getActivity()!=null)
                    ToastUtils.showError(getActivity(),"获取数据失败,请检查网络");
                if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type==1){//1是刷新
                                refreshLayout.finishRefresh(false/*,false*/);//传入false表示加载失败
                            }else {
                                refreshLayout.finishLoadMore(false/*,false*/);//传入false表示加载失败
                            }
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求成功" + call.request().toString());
                if (getActivity()!=null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type==1){//1是刷新
                                refreshLayout.finishRefresh(500/*,false*/);//传入false表示加载失败
                            }else {
                                refreshLayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
                            }
                        }
                    });

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss = body.string().trim();
                    Log.d("LogingActivity", "附近列表"+ss);
                    JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson = new Gson();
                    FuJinBean bean = gson.fromJson(jsonObject, FuJinBean.class);
                    if (bean.getCode()==2000) {
                        if (getActivity()!=null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    beanList.addAll(bean.getResult());
                                    adapter.notifyDataSetChanged();
                                    if (beanList.size()>0){
                                        shuju.setVisibility(View.GONE);
                                    }else {
                                        shuju.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                    }
                } catch (Exception e) {
                    Log.d("AllConnects", e.getMessage() + "异常");
                    if (getActivity()!=null)
                        ToastUtils.showError(getActivity(),"获取数据失败");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
